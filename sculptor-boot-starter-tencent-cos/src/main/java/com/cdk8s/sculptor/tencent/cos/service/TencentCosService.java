/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：TencentCosService.java
 * 项目名称：sculptor-boot-starter-tencent-cos
 * 项目描述：sculptor-boot-starter-tencent-cos
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.tencent.cos.service;

import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.exception.SystemException;
import com.cdk8s.sculptor.tencent.cos.properties.TencentCosParamProperties;
import com.cdk8s.sculptor.util.DatetimeUtil;
import com.cdk8s.sculptor.util.ExceptionUtil;
import com.cdk8s.sculptor.util.FileUtil;
import com.cdk8s.sculptor.util.StringUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.model.StorageClass;
import com.qcloud.cos.region.Region;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.InputStream;
import java.util.Date;



@Slf4j
public class TencentCosService {

	@Autowired
	private TencentCosParamProperties tencentCosParamProperties;


	// =====================================查询业务 start=====================================

	public String getOriginalFileName(MultipartFile file) {
		if (null == file) {
			throw new BusinessException("上传文件不能为空");
		}

		String originalFilename = file.getOriginalFilename();
		if (StringUtil.isBlank(originalFilename)) {
			throw new BusinessException("上传文件名不能为空");
		}

		return FileUtil.handleFileName(originalFilename);
	}

	public String getFileRelativePath(String uuidFileName) {
		return "/" + DatetimeUtil.formatDate(new Date(), "yyyy-MM/dd/HH") + "/" + uuidFileName;
	}

	// =====================================查询业务 end=====================================

	// =====================================操作业务 start=====================================


	public void simpleUploadFileFromStream(MultipartFile file, String cosFileRelativePath) {
		if (!cosFileRelativePath.startsWith("/")) {
			cosFileRelativePath = "/" + cosFileRelativePath;
		}

		String bucketName = tencentCosParamProperties.getBucketName();
		COSClient cosClient = getCosClient();

		try {
			InputStream inputStream = file.getInputStream();

			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(file.getSize());

			objectMetadata.setContentType(file.getContentType());

			PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, cosFileRelativePath, inputStream, objectMetadata);

			putObjectRequest.setStorageClass(StorageClass.Standard);
			PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

			String etag = putObjectResult.getETag();
		} catch (Exception e) {
			ExceptionUtil.printStackTraceAsString(e);
			throw new SystemException("cos 文件上传失败");
		} finally {
			cosClient.shutdown();
		}
	}

	public void simpleUploadFileFromLocal(File localFile, String cosFileRelativePath) {
		String bucketName = tencentCosParamProperties.getBucketName();
		COSClient cosClient = getCosClient();

		try {
			PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, cosFileRelativePath, localFile);

			putObjectRequest.setStorageClass(StorageClass.Standard);
			PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

			String etag = putObjectResult.getETag();
		} catch (Exception e) {
			ExceptionUtil.printStackTraceAsString(e);
			throw new SystemException("cos 文件上传失败");
		} finally {
			cosClient.shutdown();
		}
	}


	// =====================================操作业务 end=====================================

	// =====================================私有方法 start=====================================


	private COSClient getCosClient() {
		COSCredentials cred = new BasicCOSCredentials(tencentCosParamProperties.getSecretId(), tencentCosParamProperties.getSecretKey());
		ClientConfig clientConfig = new ClientConfig(new Region(tencentCosParamProperties.getRegion()));
		return new COSClient(cred, clientConfig);
	}

	// =====================================私有方法 end=====================================

}
