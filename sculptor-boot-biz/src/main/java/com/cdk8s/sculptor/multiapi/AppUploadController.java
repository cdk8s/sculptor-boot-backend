/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：AppUploadController.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.multiapi;

import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.properties.UploadParamProperties;
import com.cdk8s.sculptor.strategy.UserInfoContext;
import com.cdk8s.sculptor.strategy.upload.UploadStrategyContext;
import com.cdk8s.sculptor.util.FileUtil;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.UserAgentUtil;
import com.cdk8s.sculptor.util.ip.IPUtil;
import com.cdk8s.sculptor.util.response.biz.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/multiapi")
public class AppUploadController {

	@Autowired
	private UploadStrategyContext uploadStrategyContext;

	@Autowired
	private UploadParamProperties uploadParamProperties;


	// =====================================查询业务 start=====================================

	@ResponseBody
	@RequestMapping(value = "/open/upload", method = RequestMethod.POST)
	public ResponseEntity<?> openUpload(@RequestParam("file") MultipartFile multipartFile, HttpServletRequest request) {
		return upload(multipartFile, request);
	}

	@ResponseBody
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		String originalFileName = getOriginalFileName(file);
		log.info("------zch------上传用户的IP地址：<{}>", IPUtil.getIp(request));
		log.info("------zch------上传用户的UserAgent：<{}>", UserAgentUtil.getUserAgent(request));
		log.info("------zch------上传用户的文件名：<{}>", originalFileName);
		log.info("------zch------上传用户ID：<{}>", UserInfoContext.getCurrentUserId());

		FileUtil.checkImageAndVideoFormat(file, null);


		String fileSuffix = FileUtil.getFileExtension(originalFileName);
		String uuidFileName = FileUtil.getNewFileNameByUUID() + "." + fileSuffix;
		String fileRelativePath = getFileRelativePath(uuidFileName);

		String uploadStrategyBeanName = uploadParamProperties.getUploadOssTypeEnum().getDescription();
		String fileLocalFullPath = uploadStrategyContext.getFileFullPath(uploadStrategyBeanName, fileRelativePath);
		uploadStrategyContext.upload(uploadStrategyBeanName, file, fileLocalFullPath, fileRelativePath);

		String fileFullUrl = uploadStrategyContext.getFileFullUrl(uploadStrategyBeanName, fileRelativePath);
		return R.success(fileFullUrl);
	}


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


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
