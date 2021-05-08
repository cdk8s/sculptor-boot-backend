/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：UploadStrategyContext.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.strategy.upload;

import com.cdk8s.sculptor.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class UploadStrategyContext {

	private final Map<String, UploadStrategyInterface> strategyMap = new ConcurrentHashMap<>();

	@Autowired
	public UploadStrategyContext(Map<String, UploadStrategyInterface> strategyMap) {
		this.strategyMap.clear();
		strategyMap.forEach(this.strategyMap::put);
	}

	public String handleFileName(String beanName, String fileName) {
		if (StringUtil.isNotBlank(beanName)) {
			return strategyMap.get(beanName).handleFileName(fileName);
		}
		return null;
	}

	public String getFileRelativePath(String beanName, String fileName) {
		if (StringUtil.isNotBlank(beanName)) {
			return strategyMap.get(beanName).getFileRelativePath(fileName);
		}
		return null;
	}

	public String getFileFullPath(String beanName, String fileRelativePath) {
		if (StringUtil.isNotBlank(beanName)) {
			return strategyMap.get(beanName).getFileFullPath(fileRelativePath);
		}
		return null;
	}

	public String getFileFullUrl(String beanName, String fileRelativePath) {
		if (StringUtil.isNotBlank(beanName)) {
			return strategyMap.get(beanName).getFileFullUrl(fileRelativePath);
		}
		return null;
	}

	public void upload(String beanName, MultipartFile multipartFile, String fileFullPath, String fileRelativePath) {
		if (StringUtil.isNotBlank(beanName)) {
			strategyMap.get(beanName).upload(multipartFile, fileFullPath, fileRelativePath);
		}
	}

	public void uploadByAsync(String beanName, MultipartFile file, String uuidFileName, String fileFullPath, Long userId, Long bizId) {
		if (StringUtil.isNotBlank(beanName)) {
			strategyMap.get(beanName).uploadByAsync(file, uuidFileName, fileFullPath, userId, bizId);
		}
	}


}
