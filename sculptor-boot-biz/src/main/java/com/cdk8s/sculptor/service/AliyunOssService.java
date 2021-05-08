/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：AliyunOssService.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import com.cdk8s.sculptor.enums.AliyunOssAlignEnum;
import com.cdk8s.sculptor.enums.UpyunAlignEnum;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.pojo.dto.response.sysfileinfo.SysFileInfoResponseDTO;
import com.cdk8s.sculptor.properties.UploadParamProperties;
import com.cdk8s.sculptor.util.*;
import com.cdk8s.sculptor.util.code.CodecUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 该类功能暂时废弃
 */
@Slf4j
@Transactional(readOnly = true)
@Service
public class AliyunOssService {

	@Autowired
	private SysParamService sysParamService;

	@Autowired
	private UploadParamProperties uploadParamProperties;

	// =====================================查询业务 start=====================================

	@SneakyThrows
	public boolean existFile(String fileName, String filePathPrefix) {
		// String filePath = "/" + filePathPrefix + "/" + fileName;
		// Map<String, String> fileInfo = upYun.getFileInfo(filePath);
		// if (null != fileInfo) {
		// 	return true;
		// }
		return false;
	}

	/**
	 * 格式类似，有目录和文件区分：
	 */
	@SneakyThrows
	public void read(String dirPathName) {
		// if (StringUtil.isBlank(dirPathName)) {
		// 	String rootPath = (String) sysParamService.findOneValueByParamCode("path.aliyunOssRootPath.string");
		// 	dirPathName = rootPath;
		// }
		// List<UpYun.FolderItem> folderItems = upYun.readDir(dirPathName, null);
	}

	// =====================================查询业务 end=====================================

	// =====================================操作业务 start=====================================

	public String upload(Object file, String newFileName, String filePath) {
		String aliyunOssBucketName = uploadParamProperties.getAliyunOssBucketName();
		String aliyunFileUrlPrefix = (String) sysParamService.findOneValueByParamCode("url.aliyunFileUrlPrefix.string");

		try {
			boolean result = uploadMethod(aliyunOssBucketName, filePath, file);
			if (!result) {
				throw new BusinessException("上传文件失败");
			}

			String fileUrlPrefix = uploadParamProperties.getAliyunOssBucketUrl();
			if (StringUtil.isNotBlank(aliyunFileUrlPrefix)) {
				fileUrlPrefix = aliyunFileUrlPrefix;
			}

			boolean imageFlag = FileUtil.checkFileTypeByImage(newFileName);
			if (imageFlag) {
				String watermarkParams = getWatermarkParams();
				if (StringUtil.isNotBlank(watermarkParams)) {
					// 加上水印参数
					// 因为 getFilePath 不能以 / 开头，所以这里就必须补充一个
					return fileUrlPrefix + "/" + filePath + watermarkParams;
				}

				Boolean originalImageProtect = (Boolean) sysParamService.findOneValueByParamCode("switch.originalImageProtect.boolean");
				if (originalImageProtect) {
					// 如果开启原图保护，则必须有参数，不然无法访问
					String aliyunOriginalImageProtectParam = (String) sysParamService.findOneValueByParamCode("param.aliyunOriginalImageProtect.string");
					return fileUrlPrefix + "/" + filePath + "?x-oss-process=image" + aliyunOriginalImageProtectParam;
				}
			}

			return fileUrlPrefix + "/" + filePath;
		} catch (Exception e) {
			ExceptionUtil.printStackTraceAsString(e);
			throw new BusinessException("上传文件失败:" + e.getMessage());
		}
	}


	@SneakyThrows
	public void parallelUpload(File file, String filePath, Long userId, Long businessId) {

		OSS aliyunOssClient = getAliyunOssClient();
		String aliyunOssBucketName = uploadParamProperties.getAliyunOssBucketName();

		try {
			UploadFileRequest uploadFileRequest = new UploadFileRequest(aliyunOssBucketName, filePath);
			uploadFileRequest.setUploadFile(file.getPath());// 指定上传的本地文件
			uploadFileRequest.setTaskNum(2);//指定上传并发线程数，默认为1
			uploadFileRequest.setPartSize(1024 * 1024 * 3);// 指定上传的分片大小，范围为100KB~5GB，默认为文件大小/10000。
			uploadFileRequest.setEnableCheckpoint(true);// 开启断点续传，默认关闭

			// 设置上传成功回调，参数为Callback类型。
			// 官网说明：https://help.aliyun.com/document_detail/31989.html
			/**
			 * 最终回调的 body 是这样的 json 结构
			 * "bucket" -> "cdk8s"
			 * "object" -> "code-upload/2020-01/01/17/2dfc697002ce4b07ab672ec0108331a0.mp4"
			 * "mimeType" -> "video/mp4"
			 * "size" -> {Integer@15252} 108972231
			 * "userId" -> "value1"
			 * "businessId" -> "value2"
			 */
			Callback callback = new Callback();
			callback.setCallbackUrl(uploadParamProperties.getAliyunOssCallbackUrl());
			callback.setCallbackBody("{\\\"bucket\\\":${bucket},\\\"object\\\":${object},\\\"mimeType\\\":${mimeType},\\\"size\\\":${size},\\\"userId\\\":${x:var1},\\\"businessId\\\":${x:var2}}");
			callback.setCalbackBodyType(Callback.CalbackBodyType.JSON);
			// 用户自定义参数的 Key 一定要以 x: 开头且必须为小写
			// 传一个用户id，业务id
			callback.addCallbackVar("x:var1", String.valueOf(userId));
			callback.addCallbackVar("x:var2", String.valueOf(businessId));
			uploadFileRequest.setCallback(callback);

			UploadFileResult uploadResult = aliyunOssClient.uploadFile(uploadFileRequest);
			CompleteMultipartUploadResult res = uploadResult.getMultipartUploadResult();

			log.debug("------zch------ 阿里云上传断点续传结果 <{}>", res.getETag());
		} catch (Throwable throwable) {
			throw new BusinessException("上传文件失败" + throwable.getMessage());
		} finally {
			aliyunOssClient.shutdown();
		}

	}

	@SneakyThrows
	public void deleteFile(String bucketName, String filePath) {
		// 阿里云不能以 / 开头
		boolean result = StringUtil.startsWith(filePath, "/");
		if (result) {
			filePath = StringUtil.removeStart(filePath, "/");
		}

		OSS aliyunOssClient = getAliyunOssClient();
		try {
			aliyunOssClient.deleteObject(bucketName, filePath);
		} catch (Exception e) {
			throw new BusinessException("删除文件失败：" + e.getMessage());
		} finally {
			aliyunOssClient.shutdown();
		}
	}

	/**
	 * 下载文件
	 * 流式下载
	 * https://help.aliyun.com/document_detail/84823.html
	 */
	public void downloadFile(String bucketName, String filePath, HttpServletResponse response, SysFileInfoResponseDTO dto) {
		OSS aliyunOssClient = getAliyunOssClient();

		boolean result = StringUtil.startsWith(filePath, "/");
		if (result) {
			// 阿里云不允许路径以 / 开头
			filePath = StringUtil.removeStart(filePath, "/");
		}

		try {
			OSSObject ossObject = aliyunOssClient.getObject(bucketName, filePath);

			response.reset(); // 必要地清除response中的缓存信息
			response.setHeader("Content-Disposition", "attachment; filename=" + dto.getFileStorageName());// 在浏览器提示用户是保存还是下载
			response.setContentType(ossObject.getObjectMetadata().getContentType());// 根据个人需要,这个是下载文件的类型
			response.setHeader("Content-Length", String.valueOf(ossObject.getObjectMetadata().getContentLength()));// 告诉浏览器下载文件的大小

			OutputStream outputStream = response.getOutputStream();
			byte[] content = new byte[1024];
			int length;
			InputStream inputStream = ossObject.getObjectContent();
			while ((length = inputStream.read(content)) != -1) {
				outputStream.write(content, 0, length);
			}
			outputStream.write(content);
			outputStream.flush();
			outputStream.close();
			inputStream.close();

			ossObject.close();// 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
		} catch (Exception e) {
			throw new BusinessException("下载文件失败：" + e.getMessage());
		} finally {
			aliyunOssClient.shutdown();
		}
	}


	@SneakyThrows
	public void deleteFileList(String bucketName, List<String> filePathList) {
		if (CollectionUtil.isEmpty(filePathList)) {
			throw new BusinessException("被删除文件列表不能为空");
		}
		for (String filePath : filePathList) {
			try {
				deleteFile(bucketName, filePath);
			} catch (Exception e) {
				continue;
			}
			// 担心可能存在并发问题，所以这里进行了睡眠
			TimeUnit.MILLISECONDS.sleep(100);
		}
	}

	/**
	 * 删除目录必须保证目录里面的文件已经被删除，如果里面还有文件不会删除成功，也不会报错
	 */
	@SneakyThrows
	public void deleteDir(String bucketName, String dirPath) {
		OSS aliyunOssClient = getAliyunOssClient();
		String rootPath = (String) sysParamService.findOneValueByParamCode("path.aliyunOssRootPath.string");
		// 阿里云不能以 / 开头
		String path = rootPath + buildPath(dirPath, true);
		try {
			aliyunOssClient.deleteObject(bucketName, path);
		} catch (Exception e) {
			throw new BusinessException("删除文件失败：" + e.getMessage());
		} finally {
			aliyunOssClient.shutdown();
		}
	}

	/**
	 * 已经存在的目录，多次调用该方法，并不会删除里面已经存在的旧文件
	 */
	@SneakyThrows
	public void createDir(String dirPath) {
		// // 因为没有创建目录的 API，所以我们自己创建一个文本文件，然后再删除
		// String tempFileName = "byCreateDir.txt";
		// String rootPath = (String) sysParamService.findOneValueByParamCode("path.aliyunOssRootPath.string");
		// String filePath = "/" + rootPath + buildPath(dirPath, true) + tempFileName;
		// boolean result = upYun.writeFile(filePath, "thisIsFileContentToTemp");
		// if (!result) {
		// 	throw new BusinessException("创建目录失败");
		// }
		//
		// // 有时候对方接口会报：concurrent put or delete，可能存在并发问题，所以这里进行了睡眠
		// TimeUnit.SECONDS.sleep(1);
		//
		// // 再删除文件
		// deleteFile(filePath);
	}

	public String getWatermarkParams() {
		// 加水印，官网文档：https://help.aliyun.com/document_detail/44957.html
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

		if (StringUtil.isBlank(param)) {
			return null;
		}
		return "?x-oss-process=image" + param;
	}

	public String getThumbnailParams() {
		// 缩放，官网文档：https://help.aliyun.com/document_detail/44688.html
		Integer width = (Integer) sysParamService.findOneValueByParamCode("width.materialLibraryResize.integer");
		Integer height = (Integer) sysParamService.findOneValueByParamCode("height.materialLibraryResize.integer");
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("?x-oss-process=image");
		stringBuilder.append("/resize,");
		stringBuilder.append("w_");
		stringBuilder.append(width);
		stringBuilder.append(",");
		stringBuilder.append("h_");
		stringBuilder.append(height);
		return stringBuilder.toString();
	}

	public String getFilePath(String fileName, Boolean addDatePathEnable, String folderPath) {
		String rootPathName = (String) sysParamService.findOneValueByParamCode("path.aliyunOssRootPath.string");

		// 如果文件名有空格，又拍云会报错
		// 阿里云不允许以 / 开头，这个跟又拍云不一样
		fileName = StringUtil.replace(fileName, " ", "_");
		String filePath = rootPathName + "/" + fileName;
		if (addDatePathEnable) {
			filePath = rootPathName + "/" + DatetimeUtil.formatDate(new Date(), "yyyy-MM/dd/HH") + "/" + fileName;
		} else {
			filePath = rootPathName + buildPath(folderPath, true) + fileName;
		}
		return filePath;
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

	@SneakyThrows
	private boolean uploadMethod(String bucketName, String filePath, Object file) {
		if (file instanceof MultipartFile) {
			return uploadMethod(bucketName, filePath, (MultipartFile) file);
		}
		return uploadMethod(bucketName, filePath, (File) file);
	}


	@SneakyThrows
	private boolean uploadMethod(String bucketName, String filePath, MultipartFile file) {
		OSS aliyunOssClient = getAliyunOssClient();
		try {
			PutObjectResult res = aliyunOssClient.putObject(bucketName, filePath, file.getInputStream());
			if (res == null) {
				return false;
			}
		} catch (Exception e) {
			throw new BusinessException("上传文件失败" + e.getMessage());
		} finally {
			aliyunOssClient.shutdown();
		}
		return true;
	}

	@SneakyThrows
	private boolean uploadMethod(String bucketName, String filePath, File file) {
		OSS aliyunOssClient = getAliyunOssClient();
		try {
			PutObjectResult res = aliyunOssClient.putObject(bucketName, filePath, file);
			if (res == null) {
				return false;
			}
		} catch (Exception e) {
			throw new BusinessException("上传文件失败" + e.getMessage());
		} finally {
			aliyunOssClient.shutdown();
		}
		return true;
	}


	private String imageWatermarkParam() {
		// 又拍云是要以 / 开头，阿里云是不能 / 开头，所以这里要处理下
		String path = (String) sysParamService.findOneValueByParamCode("path.imageWatermark.string");
		boolean result = StringUtil.startsWith(path, "/");
		if (result) {
			path = StringUtil.removeStart(path, "/");
		}

		Integer percent = (Integer) sysParamService.findOneValueByParamCode("percent.imageWatermark.integer");
		// 对水印图进行缩放比例设置。又拍云可以单独一个参数设置，但是阿里云不行，必须先调整水印图
		path = path + "?x-oss-process=image/resize,p_" + percent;

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("/watermark,image_");
		stringBuilder.append(CodecUtil.base64EncodeByAliyunWatermark(path));
		stringBuilder.append(",");

		stringBuilder.append(commonWatermarkParam(true));

		return stringBuilder.toString();
	}

	private String textWatermarkParam() {
		String content = (String) sysParamService.findOneValueByParamCode("content.textWatermark.string");
		Integer size = (Integer) sysParamService.findOneValueByParamCode("size.textWatermark.integer");
		String color = (String) sysParamService.findOneValueByParamCode("color.textWatermark.string");

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("/watermark,text_");
		stringBuilder.append(CodecUtil.base64EncodeByAliyunWatermark(content));
		stringBuilder.append(",");

		stringBuilder.append("size_");
		stringBuilder.append(size);
		stringBuilder.append(",");

		stringBuilder.append("color_");
		stringBuilder.append(color);
		stringBuilder.append(",");

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

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("t_");
		stringBuilder.append(transparency);
		stringBuilder.append(",");

		stringBuilder.append("g_");
		// 由于又拍云和阿里云的值不一致，且阿里云是简写方式，不易于阅读，所以我们默认以又拍云的枚举值为标准，这里就需要进行转换
		UpyunAlignEnum enumByDescription = UpyunAlignEnum.getEnumByDescription(align);
		String descriptionByCode = AliyunOssAlignEnum.getDescriptionByCode(enumByDescription.getCode());
		stringBuilder.append(descriptionByCode);
		stringBuilder.append(",");

		stringBuilder.append("x_");
		stringBuilder.append(imageWatermarkMarginX);
		stringBuilder.append(",");

		stringBuilder.append("y_");
		stringBuilder.append(imageWatermarkMarginY);

		return stringBuilder.toString();
	}

	private OSS getAliyunOssClient() {
		return new OSSClientBuilder().build(uploadParamProperties.getAliyunOssEndpointUrl(), uploadParamProperties.getAliyunOssAccessKeyId(), uploadParamProperties.getAliyunOssAccessKeySecret());
	}
	// =====================================私有方法 end=====================================

}

