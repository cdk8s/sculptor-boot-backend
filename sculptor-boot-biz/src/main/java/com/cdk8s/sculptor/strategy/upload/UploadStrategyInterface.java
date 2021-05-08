/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：UploadStrategyInterface.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.strategy.upload;


import org.springframework.scheduling.annotation.Async;
import org.springframework.web.multipart.MultipartFile;

public interface UploadStrategyInterface {

	// 处理文件名上的特殊符号
	String handleFileName(String fileName);

	String getFileRelativePath(String fileName);

	String getFileFullPath(String fileRelativePath);

	String getFileFullUrl(String fileRelativePath);

	void upload(MultipartFile multipartFile, String fileFullPath, String fileRelativePath);

	@Async
	void uploadByAsync(MultipartFile file, String uuidFileName, String fileFullPath, Long userId, Long bizId);

}
