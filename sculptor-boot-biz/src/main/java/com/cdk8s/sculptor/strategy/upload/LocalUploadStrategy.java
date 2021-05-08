/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：LocalUploadStrategy.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.strategy.upload;


import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.exception.SystemException;
import com.cdk8s.sculptor.properties.UploadParamProperties;
import com.cdk8s.sculptor.service.SysParamService;
import com.cdk8s.sculptor.util.DatetimeUtil;
import com.cdk8s.sculptor.util.ExceptionUtil;
import com.cdk8s.sculptor.util.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

@Slf4j
@Service("localUploadStrategy")
public class LocalUploadStrategy implements UploadStrategyInterface {

	@Autowired
	private SysParamService sysParamService;

	@Autowired
	private UploadParamProperties uploadParamProperties;

	// =====================================业务处理 start=====================================
	@Override
	public String handleFileName(String fileName) {
		return FileUtil.handleFileName(fileName);
	}

	@Override
	public String getFileRelativePath(String fileName) {
		// 如果文件名有空格，进行替换
		Date nowDate = new Date();
		String yearAndMonth = DatetimeUtil.formatDate(nowDate, "yyyy-MM");
		String day = DatetimeUtil.formatDate(nowDate, "dd");
		String hour = DatetimeUtil.formatDate(nowDate, "HH");
		return File.separator + yearAndMonth + File.separator + day + File.separator + hour + File.separator + fileName;
	}

	@Override
	public String getFileFullPath(String fileRelativePath) {
		return uploadParamProperties.getLocalUploadStoragePath() + fileRelativePath;
	}

	@Override
	public String getFileFullUrl(String fileRelativePath) {
		Object obj = sysParamService.findOneValueByParamCode("url.localFileUrlPrefix.string");
		if (null == obj) {
			throw new SystemException("无法找到本地上传资源域名前缀");
		}

		String localFileUrlPrefix = (String) obj;

		Object localFileUrlPrefixByNginxObject = sysParamService.findOneValueByParamCode("switch.localFileUrlNginx.boolean");
		if (null != localFileUrlPrefixByNginxObject) {
			Boolean localFileUrlPrefixByNginx = (Boolean) localFileUrlPrefixByNginxObject;
			if (localFileUrlPrefixByNginx) {
				// 直接通过 nginx 访问静态资源，所以就不需要配置 java 的 resource 映射路径了
				return localFileUrlPrefix + fileRelativePath;
			}
		}

		// 通过 tomcat 访问
		return localFileUrlPrefix + uploadParamProperties.getLocalUploadFileUrlRelativePath() + fileRelativePath;
	}

	@Override
	public void upload(MultipartFile multipartFile, String fileLocalFullPath, String fileRelativePath) {
		try {
			File dest = new File(fileLocalFullPath).getCanonicalFile();
			// 检测是否存在目录
			if (!dest.getParentFile().exists()) {
				dest.getParentFile().mkdirs();
			}
			// 文件写入
			multipartFile.transferTo(dest);
		} catch (Exception e) {
			ExceptionUtil.printStackTraceAsString(e);
			throw new BusinessException("上传文件失败：" + e.getMessage());
		}
	}

	@Override
	public void uploadByAsync(MultipartFile file, String uuidFileName, String fileFullPath, Long userId, Long bizId) {

	}


	// =====================================业务处理 end=====================================

	// =====================================私有方法 start=====================================


	// =====================================私有方法 end=====================================

}
