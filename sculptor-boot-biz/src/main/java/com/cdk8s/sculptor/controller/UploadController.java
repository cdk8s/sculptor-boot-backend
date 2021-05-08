/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：UploadController.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.controller;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.aliyuncs.sts.model.v20150401.AssumeRoleRequest;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.BooleanEnum;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.pojo.bo.service.sysfileinfo.SysFileInfoCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.thirdapi.AliyunOssStsCredential;
import com.cdk8s.sculptor.properties.UploadParamProperties;
import com.cdk8s.sculptor.service.SysFileInfoService;
import com.cdk8s.sculptor.service.UploadService;
import com.cdk8s.sculptor.strategy.upload.UploadStrategyContext;
import com.cdk8s.sculptor.util.ExceptionUtil;
import com.cdk8s.sculptor.util.FileUtil;
import com.cdk8s.sculptor.util.JsonUtil;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import com.cdk8s.sculptor.util.response.biz.R;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/api/upload")
public class UploadController {

	@Autowired
	private UploadService uploadService;

	@Autowired
	private SysFileInfoService sysFileInfoService;

	@Autowired
	private UploadParamProperties uploadParamProperties;

	@Autowired
	private UploadStrategyContext uploadStrategyContext;

	// =====================================查询业务 start=====================================

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	@RequestMapping(value = "/getAliyunOssStsCredential", method = RequestMethod.POST)
	public ResponseEntity<?> getAliyunOssStsCredential() {

		String roleSessionName = "oss-sts-frontend";

		try {

			DefaultProfile.addEndpoint("", "", "Sts", uploadParamProperties.getAliyunOssStsEndpoint());

			IClientProfile profile = DefaultProfile.getProfile("", uploadParamProperties.getAliyunOssAccessKeyId(), uploadParamProperties.getAliyunOssAccessKeySecret());
			DefaultAcsClient client = new DefaultAcsClient(profile);
			final AssumeRoleRequest request = new AssumeRoleRequest();
			request.setMethod(MethodType.POST);
			request.setRoleArn(uploadParamProperties.getAliyunOssStsRoleArn());
			request.setRoleSessionName(roleSessionName);
			request.setDurationSeconds(3600L);
			final AssumeRoleResponse response = client.getAcsResponse(request);
			AssumeRoleResponse.Credentials credentials = response.getCredentials();
			String expiration = credentials.getExpiration();


			DateTime dateTime = DateUtil.parseUTC(expiration);
			String newExpiration = dateTime.toString("yyyy-MM-dd HH:mm:ss");

			AliyunOssStsCredential aliyunOssStsCredential = new AliyunOssStsCredential();
			aliyunOssStsCredential.setSecurityToken(credentials.getSecurityToken());
			aliyunOssStsCredential.setAccessKeySecret(credentials.getAccessKeySecret());
			aliyunOssStsCredential.setAccessKeyId(credentials.getAccessKeyId());
			aliyunOssStsCredential.setExpiration(newExpiration);
			aliyunOssStsCredential.setRegion(uploadParamProperties.getAliyunOssRegion());
			aliyunOssStsCredential.setBucketName(uploadParamProperties.getAliyunOssBucketName());
			return R.success(aliyunOssStsCredential);
		} catch (ClientException e) {
			ExceptionUtil.printStackTraceAsString(e);
		}
		return R.failure("获取 aliyun oss credential 失败，请联系管理员进行处理");
	}


	/**
	 * 普通上传：同步
	 */
	@ResponseBody
	@RequestMapping(value = "/sync", method = RequestMethod.POST)
	public ResponseEntity<?> sync(@RequestParam("file") MultipartFile file) {
		String originalFileName = getOriginalFileName(file);

		String fileSuffix = FileUtil.getFileExtension(originalFileName);
		String uuidFileName = FileUtil.getNewFileNameByUUID() + "." + fileSuffix;
		String fileRelativePath = getFileRelativePath(uuidFileName);

		String uploadStrategyBeanName = uploadParamProperties.getUploadOssTypeEnum().getDescription();
		String fileLocalFullPath = uploadStrategyContext.getFileFullPath(uploadStrategyBeanName, fileRelativePath);
		uploadStrategyContext.upload(uploadStrategyBeanName, file, fileLocalFullPath, fileRelativePath);

		String fileFullUrl = uploadStrategyContext.getFileFullUrl(uploadStrategyBeanName, fileRelativePath);
		return R.success(fileFullUrl);
	}

	/**
	 * 资料库上传：同步
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadToMaterialLibrarySync", method = RequestMethod.POST)
	public ResponseEntity<?> uploadToMaterialLibrarySync(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		Long currentUserId = GlobalConstant.TOP_ADMIN_USER_ID;

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


		String fileStoragePath = StringUtil.remove(fileFullPath, "/" + uuidFileName);
		if (!StringUtil.startsWith(fileStoragePath, "/")) {

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

	/**
	 * 资料库上传：异步
	 */
	@ResponseBody
	@RequestMapping(value = "/uploadToMaterialLibrary", method = RequestMethod.POST)
	public ResponseEntity<?> uploadMaterialLibrary(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		Long currentUserId = GlobalConstant.TOP_ADMIN_USER_ID;

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


		String fileStoragePath = StringUtil.remove(fileFullPath, "/" + uuidFileName);
		if (!StringUtil.startsWith(fileStoragePath, "/")) {

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

		uploadStrategyContext.uploadByAsync(uploadStrategyBeanName, file, uuidFileName, fileFullPath, currentUserId, bizId);


		return R.success(bizId);
	}

	/**
	 * 富文本上传：同步
	 */
	@SneakyThrows
	@RequestMapping(value = "/uploadToWysiwyg", method = RequestMethod.POST)
	public void uploadToWysiwyg(@RequestParam("file") MultipartFile file, HttpServletResponse response) {

		if (null == file) {
			throw new BusinessException("上传文件不能为空");
		}

		String originalFilename = file.getOriginalFilename();
		if (StringUtil.isBlank(originalFilename)) {
			throw new BusinessException("上传文件名不能为空");
		}

		boolean checkFileTypeByImage = FileUtil.checkFileTypeByImage(originalFilename);

		String fileNameByUUID = FileUtil.getNewFileNameByUUID() + "." + FileUtil.getFileExtension(originalFilename);
		String filePath = uploadService.getFilePath(fileNameByUUID, true, null);

		Map<String, Object> map = new HashMap<>();
		String result;
		if (checkFileTypeByImage) {
			result = uploadService.uploadImage(file, fileNameByUUID, filePath);
			map.put("link", result);
			String toJson = JsonUtil.toJson(map);
			response.setContentType(GlobalConstant.JSON_MEDIA_TYPE);
			response.getOutputStream().write(toJson.getBytes());
			return;
		}

		boolean checkFileTypeByVideo = FileUtil.checkFileTypeByVideo(originalFilename);
		if (checkFileTypeByVideo) {
			result = uploadService.uploadVideo(file, fileNameByUUID, filePath);
		} else {
			result = uploadService.uploadFile(file, fileNameByUUID, filePath);
		}

		map.put("link", result);
		String toJson = JsonUtil.toJson(map);
		response.setContentType(GlobalConstant.JSON_MEDIA_TYPE);
		response.getOutputStream().write(toJson.getBytes());
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
