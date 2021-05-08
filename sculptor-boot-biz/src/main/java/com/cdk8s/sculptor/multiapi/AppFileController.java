/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：AppFileController.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.multiapi;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.BooleanEnum;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapstruct.SysFileInfoMapStruct;
import com.cdk8s.sculptor.pojo.bo.service.sysfileinfo.SysFileInfoCreateServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.sysfileinfo.SysFileInfoPageQueryParam;
import com.cdk8s.sculptor.pojo.dto.response.sysfileinfo.SysFileInfoResponseDTO;
import com.cdk8s.sculptor.properties.UploadParamProperties;
import com.cdk8s.sculptor.service.SysFileInfoService;
import com.cdk8s.sculptor.service.UploadService;
import com.cdk8s.sculptor.strategy.upload.UploadStrategyContext;
import com.cdk8s.sculptor.util.FileUtil;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import com.cdk8s.sculptor.util.response.biz.R;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/multiapi")
public class AppFileController {

	@Autowired
	private SysFileInfoService sysFileInfoService;

	@Autowired
	private SysFileInfoMapStruct sysFileInfoMapStruct;

	@Autowired
	private UploadService uploadService;

	@Autowired
	private UploadParamProperties uploadParamProperties;

	@Autowired
	private UploadStrategyContext uploadStrategyContext;

	// =====================================查询业务 start=====================================

	@RequestMapping(value = "/open/t298p_sys_file_info", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysFileInfoPageQueryParam param) {
		PageInfo result = sysFileInfoService.findPageByServiceBO(sysFileInfoMapStruct.pageQueryParamToServiceBO(param));
		List<SysFileInfoResponseDTO> list = sysFileInfoMapStruct.toResponseDTOList(result.getList());

		// if (CollectionUtil.isNotEmpty(list)) {
		// for (SysFileInfoResponseDTO dto : list) {

		// }
		// }

		result.setList(list);
		return R.success(result);
	}


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	@RequestMapping(value = "/open/t299c_sys_file_info", method = RequestMethod.POST)
	public ResponseEntity<?> uploadToMaterialLibrarySync(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		Long currentUserId = GlobalConstant.TOP_ADMIN_USER_ID;

		// Long currentUserId = UserInfoContext.getCurrentUserId();
		// if (null == currentUserId) {
		// 	throw new BusinessException("必须登录才可以用资料库上传文件");
		// }

		String folderId = request.getParameter("folderId");
		if (StringUtil.isBlank(folderId) || StringUtil.equalsIgnoreCase("undefined", folderId)) {
			throw new BusinessException("必须先选择上传目录");
		}

		String originalFileName = getOriginalFileName(file);
		String fileSuffix = FileUtil.getFileExtension(originalFileName);
		String uuidFileName = FileUtil.getNewFileNameByUUID() + "." + fileSuffix;
		String fileRelativePath = getFileRelativePath(uuidFileName);

		String uploadStrategyBeanName = uploadParamProperties.getUploadOssTypeEnum().getDescription();
		String fileFullPath = uploadStrategyContext.getFileFullPath(uploadStrategyBeanName, fileRelativePath);
		String fileFullUrl = uploadStrategyContext.getFileFullUrl(uploadStrategyBeanName, fileRelativePath);

		// 我们数据库 fileStoragePath 不存文件名，这里先删除文件名
		String fileStoragePath = StringUtil.remove(fileFullPath, "/" + uuidFileName);
		if (!StringUtil.startsWith(fileStoragePath, "/")) {
			// 有些 OSS 不允许 / 开头，但是我们存储到自己数据库表中必须用 / 开头，所以这里做了判断
			fileStoragePath = "/" + fileStoragePath;
		}

		Long bizId = GenerateIdUtil.getId();
		SysFileInfoCreateServiceBO serviceBO = new SysFileInfoCreateServiceBO();
		serviceBO.setId(bizId);
		serviceBO.setFolderId(Long.valueOf(folderId));
		serviceBO.setBoolOssCompleteEnum(BooleanEnum.NO.getCode());
		serviceBO.setBoolTopEnum(BooleanEnum.NO.getCode());
		serviceBO.setFileShowName(originalFileName);
		serviceBO.setFileStorageName(uuidFileName);
		serviceBO.setFileSuffix(fileSuffix);
		serviceBO.setFileStoragePath(fileStoragePath);
		serviceBO.setFileFullUrl(fileFullUrl);
		serviceBO.setFileStorageTypeEnum(uploadParamProperties.getUploadOssTypeEnum().getCode());
		serviceBO.setFileSize(file.getSize());
		serviceBO.setDescription(originalFileName + "_" + uuidFileName);
		serviceBO.setCreateUserId(currentUserId);
		serviceBO.setUpdateUserId(currentUserId);

		Integer result = sysFileInfoService.create(serviceBO);
		if (result == 0) {
			throw new BusinessException("插入文件索引失败，请重新上传");
		}

		String fileLocalFullPath = uploadStrategyContext.getFileFullPath(uploadStrategyBeanName, fileRelativePath);
		uploadStrategyContext.upload(uploadStrategyBeanName, file, fileLocalFullPath, fileRelativePath);

		return R.success();
	}


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private String getOriginalFileName(MultipartFile file) {
		if (null == file) {
			throw new BusinessException("上传文件不能为空");
		}

		String originalFilename = file.getOriginalFilename();
		if (StringUtil.isBlank(originalFilename)) {
			throw new BusinessException("上传文件名不能为空");
		}

		String uploadStrategyBeanName = uploadParamProperties.getUploadOssTypeEnum().getDescription();
		return uploadStrategyContext.handleFileName(uploadStrategyBeanName, originalFilename);
	}

	private String getFileRelativePath(String fileName) {
		String uploadStrategyBeanName = uploadParamProperties.getUploadOssTypeEnum().getDescription();
		return uploadStrategyContext.getFileRelativePath(uploadStrategyBeanName, fileName);
	}

	// =====================================私有方法 end=====================================

}
