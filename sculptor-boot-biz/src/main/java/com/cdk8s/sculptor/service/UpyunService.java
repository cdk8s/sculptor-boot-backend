/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：UpyunService.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service;

import com.UpYun;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.properties.UploadParamProperties;
import com.cdk8s.sculptor.util.*;
import com.cdk8s.sculptor.util.code.CodecUtil;
import com.upyun.Params;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Transactional(readOnly = true)
@Service
public class UpyunService {

	@Autowired
	private UploadParamProperties uploadParamProperties;

	@Autowired
	private SysParamService sysParamService;

	// =====================================查询业务 start=====================================

	@SneakyThrows
	public boolean existFile(String fileName, String filePathPrefix) {
		String filePath = "/" + filePathPrefix + "/" + fileName;
		Map<String, String> fileInfo = getUpyun().getFileInfo(filePath);
		if (null != fileInfo) {
			return true;
		}
		return false;
	}

	/**
	 * 格式类似，有目录和文件区分：
	 * [
	 * {
	 * "name": "QQ20191202-141924.png",
	 * "type": "File",
	 * "size": 443861,
	 * "date": 1577383577000
	 * },
	 * {
	 * "name": "2019-10-13",
	 * "type": "Folder",
	 * "size": 0,
	 * "date": 1523944056000
	 * },
	 * {
	 * "name": "2019-10-12",
	 * "type": "Folder",
	 * "size": 0,
	 * "date": 1569305863000
	 * }
	 * ]
	 */
	@SneakyThrows
	public void read(String dirPathName) {
		if (StringUtil.isBlank(dirPathName)) {
			String rootPath = (String) sysParamService.findOneValueByParamCode("path.upyunRootPath.string");
			dirPathName = rootPath;
		}

		List<UpYun.FolderItem> folderItems = getUpyun().readDir(dirPathName, null);
	}

	// =====================================查询业务 end=====================================

	// =====================================操作业务 start=====================================


	@SneakyThrows
	public List<String> upload(MultipartFile[] files) {
		List<String> fileUrlList = new ArrayList<>();
		for (MultipartFile file : files) {
			if (null == file) {
				continue;
			}
			String newFileName = FileUtil.getNewFileNameByUUID() + "." + FileUtil.getFileExtension(file.getOriginalFilename());
			String rootPath = (String) sysParamService.findOneValueByParamCode("path.upyunRootPath.string");
			String fileUrl = upload(file, newFileName, rootPath);
			fileUrlList.add(fileUrl);
		}
		return fileUrlList;
	}


	@SneakyThrows
	public void deleteFile(String filePath) {
		String rootPath = (String) sysParamService.findOneValueByParamCode("path.upyunRootPath.string");
		String path = "/" + rootPath + buildPath(filePath, false);

		try {
			getUpyun().deleteFile(path, null);
		} catch (Exception e) {
			throw new BusinessException("删除文件失败：" + e.getMessage());
		}
	}

	@SneakyThrows
	public void deleteFileList(List<String> filePathList) {
		if (CollectionUtil.isEmpty(filePathList)) {
			throw new BusinessException("被删除文件列表不能为空");
		}

		UpYun upyun = getUpyun();

		String path;
		for (String filePath : filePathList) {
			String rootPath = (String) sysParamService.findOneValueByParamCode("path.upyunRootPath.string");
			path = "/" + rootPath + buildPath(filePath, false);
			try {
				upyun.deleteFile(path, null);
			} catch (Exception e) {
				// 删除不存在的文件会报：file or directory not found，这时候不管，让它继续删除其他文件
				continue;
			}
			// 有时候对方接口会报：concurrent put or delete，可能存在并发问题，所以这里进行了睡眠
			TimeUnit.MILLISECONDS.sleep(300);
		}
	}

	/**
	 * 删除目录必须保证目录里面的文件已经被删除
	 * 目录里面还会有目录，也有文件，很难控制要不要一直迭代进行删除，所以还是交给用户自己先批量删除文件
	 */
	@SneakyThrows
	public void deleteDir(String dirPath) {
		String rootPath = (String) sysParamService.findOneValueByParamCode("path.upyunRootPath.string");
		String filePath = "/" + rootPath + buildPath(dirPath, true);

		try {
			getUpyun().rmDir(filePath);
		} catch (Exception e) {
			throw new BusinessException("删除目录失败：" + e.getMessage());
		}
	}

	/**
	 * 已经存在的目录，多次调用该方法，并不会删除里面已经存在的旧文件
	 */
	@SneakyThrows
	public void createDir(String dirPath) {
		String rootPath = (String) sysParamService.findOneValueByParamCode("path.upyunRootPath.string");
		String filePath = "/" + rootPath + buildPath(dirPath, true);

		boolean result = getUpyun().mkDir(filePath, true);
		if (!result) {
			throw new BusinessException("创建目录失败");
		}
	}


	public String getWatermarkParams() {
		// 加水印，官网文档：http://docs.upyun.com/cloud/image/#_14
		Boolean imageWatermarkSwitch = (Boolean) sysParamService.findOneValueByParamCode("switch.imageWatermark.boolean");
		Boolean textWatermarkSwitch = (Boolean) sysParamService.findOneValueByParamCode("switch.textWatermark.boolean");
		String param = "";
		if (imageWatermarkSwitch && textWatermarkSwitch) {
			param = textWatermarkParam() + imageWatermarkParam();
		} else if (imageWatermarkSwitch) {
			param = imageWatermarkParam();
		} else if (textWatermarkSwitch) {
			param = textWatermarkParam();
		}

		return "!" + param;
	}

	public String getThumbnailParams() {
		// 缩放，官网文档：http://docs.upyun.com/cloud/image/#_12
		Integer width = (Integer) sysParamService.findOneValueByParamCode("width.materialLibraryResize.integer");
		Integer height = (Integer) sysParamService.findOneValueByParamCode("height.materialLibraryResize.integer");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("!");
		stringBuilder.append("/fw/");
		stringBuilder.append(width);
		stringBuilder.append("/fh");
		stringBuilder.append(height);
		return stringBuilder.toString();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private String buildPath(String path, Boolean needEnd) {
		boolean result = StringUtil.startsWith(path, "/");
		if (!result) {
			path = "/" + path;
		}
		boolean resultByEnd = StringUtil.endsWith(path, "/");
		if (resultByEnd) {
			if (!needEnd) {
				path = StringUtil.removeEnd(path, "/");
			}
		} else {
			if (needEnd) {
				path = path + "/";
			}
		}
		return path;
	}

	private String upload(Object file, String fileName, String upyunRootPathName) {
		String filePath = getFilePath(fileName, upyunRootPathName);
		try {
			boolean result = uploadMethod(filePath, file);
			if (!result) {
				throw new BusinessException("上传文件失败");
			}
			String upyunFileUrlPrefix = (String) sysParamService.findOneValueByParamCode("url.upyunFileUrlPrefix.string");
			String watermarkParams = getWatermarkParams();

			if (StringUtil.isNotBlank(watermarkParams)) {
				// 加上水印参数
				return upyunFileUrlPrefix + filePath + watermarkParams;
			}

			Boolean originalImageProtect = (Boolean) sysParamService.findOneValueByParamCode("switch.originalImageProtect.boolean");
			if (originalImageProtect) {
				// 如果开启原图保护，则必须有参数，不然无法访问
				String upyunOriginalImageProtectParam = (String) sysParamService.findOneValueByParamCode("param.upyunOriginalImageProtect.string");
				return upyunFileUrlPrefix + filePath + "!" + upyunOriginalImageProtectParam;
			}

			return upyunFileUrlPrefix + filePath;
		} catch (Exception e) {
			ExceptionUtil.printStackTraceAsString(e);
			throw new BusinessException("上传文件失败:" + e.getMessage());
		}
	}

	private String getFilePath(String fileName, String upyunRootPathName) {
		// 如果文件名有空格，又拍云会报错
		fileName = StringUtil.replace(fileName, " ", "_");
		String filePath = "/" + upyunRootPathName + "/" + DatetimeUtil.formatDate(new Date(), "yyyy-MM/dd/HH") + "/" + fileName;
		return filePath;
	}

	@SneakyThrows
	private boolean uploadMethod(String filePath, Object file) {
		if (file instanceof MultipartFile) {
			return uploadMethod(filePath, (MultipartFile) file);
		}
		return uploadMethod(filePath, (File) file);
	}


	@SneakyThrows
	private boolean uploadMethod(String filePath, MultipartFile file) {
		return getUpyun().writeFile(filePath, file.getInputStream(), true, buildUploadParams());
	}

	@SneakyThrows
	private boolean uploadMethod(String filePath, File file) {
		UpYun upyun = getUpyun();
		upyun.setContentMD5(UpYun.md5(file));
		return upyun.writeFile(filePath, file, true, buildUploadParams());
	}

	private Map<String, String> buildUploadParams() {
		// 原图保护密钥：http://docs.upyun.com/cloud/image/#_7
		Map<String, String> paramsMap = new HashMap<>();
		Boolean originalImageProtect = (Boolean) sysParamService.findOneValueByParamCode("switch.originalImageProtect.boolean");

		if (originalImageProtect) {
			String upyunOriginalImageProtect = (String) sysParamService.findOneValueByParamCode("secret.upyunOriginalImageProtect.string");
			paramsMap.put(Params.CONTENT_SECRET, upyunOriginalImageProtect);
		}

		return paramsMap;
	}


	private String imageWatermarkParam() {
		String path = (String) sysParamService.findOneValueByParamCode("path.imageWatermark.string");
		Integer percent = (Integer) sysParamService.findOneValueByParamCode("percent.imageWatermark.integer");

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("/watermark/url/");
		stringBuilder.append(CodecUtil.byteToBase64StringByGuava(path.getBytes()));

		stringBuilder.append("/percent/");
		stringBuilder.append(percent);

		stringBuilder.append(commonWatermarkParam(true));

		return stringBuilder.toString();
	}

	private String textWatermarkParam() {
		String content = (String) sysParamService.findOneValueByParamCode("content.textWatermark.string");
		Integer size = (Integer) sysParamService.findOneValueByParamCode("size.textWatermark.integer");
		String color = (String) sysParamService.findOneValueByParamCode("color.textWatermark.string");

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("/watermark/text/");
		stringBuilder.append(CodecUtil.byteToBase64StringByGuava(content.getBytes()));

		stringBuilder.append("/size/");
		stringBuilder.append(size);

		stringBuilder.append("/color/");
		stringBuilder.append(color);

		stringBuilder.append(commonWatermarkParam(false));

		return stringBuilder.toString();
	}

	private String commonWatermarkParam(boolean isImage) {
		String align;
		Integer transparency;
		Integer imageWatermarkMarginX;
		Integer imageWatermarkMarginY;

		if (isImage) {
			align = (String) sysParamService.findOneValueByParamCode("align.imageWatermark.string");
			transparency = (Integer) sysParamService.findOneValueByParamCode("transparency.imageWatermark.integer");
			imageWatermarkMarginX = (Integer) sysParamService.findOneValueByParamCode("margin.imageWatermarkMarginX.integer");
			imageWatermarkMarginY = (Integer) sysParamService.findOneValueByParamCode("margin.imageWatermarkMarginY.integer");
		} else {
			align = (String) sysParamService.findOneValueByParamCode("align.textWatermark.string");
			transparency = (Integer) sysParamService.findOneValueByParamCode("transparency.textWatermark.integer");
			imageWatermarkMarginX = (Integer) sysParamService.findOneValueByParamCode("margin.textWatermarkMarginX.integer");
			imageWatermarkMarginY = (Integer) sysParamService.findOneValueByParamCode("margin.textWatermarkMarginY.integer");
		}

		String margin = imageWatermarkMarginX + "x" + imageWatermarkMarginY;

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("/align/");
		stringBuilder.append(align);

		stringBuilder.append("/opacity/");
		stringBuilder.append(transparency);

		stringBuilder.append("/margin/");
		stringBuilder.append(margin);

		return stringBuilder.toString();
	}

	private UpYun getUpyun() {
		UpYun upyun = new UpYun(uploadParamProperties.getUpyunBucketName(), uploadParamProperties.getUpyunUsername(), uploadParamProperties.getUpyunPassword());
		upyun.setTimeout(30);
		upyun.setApiDomain(UpYun.ED_AUTO);
		return upyun;
	}

	// =====================================私有方法 end=====================================

}

