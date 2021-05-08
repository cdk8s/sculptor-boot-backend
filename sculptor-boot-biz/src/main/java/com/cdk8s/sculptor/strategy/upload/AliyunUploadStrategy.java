/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：AliyunUploadStrategy.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.strategy.upload;


import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.*;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.exception.SystemException;
import com.cdk8s.sculptor.properties.UploadParamProperties;
import com.cdk8s.sculptor.service.SysParamService;
import com.cdk8s.sculptor.util.DatetimeUtil;
import com.cdk8s.sculptor.util.FileUtil;
import com.cdk8s.sculptor.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Date;

@Slf4j
@Service("aliyunUploadStrategy")
public class AliyunUploadStrategy implements UploadStrategyInterface {

	@Autowired
	private UploadParamProperties uploadParamProperties;

	@Autowired
	private SysParamService sysParamService;

	// =====================================业务处理 start=====================================

	@Override
	public String handleFileName(String fileName) {
		return FileUtil.handleFileName(fileName);
	}

	@Override
	public String getFileRelativePath(String fileName) {
		Object obj = sysParamService.findOneValueByParamCode("path.aliyunOssRootPath.string");
		if (null == obj) {
			throw new SystemException("无法找到阿里云预设根路径");
		}

		String rootPathName = (String) obj;

		if (StringUtil.startsWith(rootPathName, "/")) {
			// 阿里云不允许以 / 开头，这个跟又拍云不一样
			rootPathName = StringUtil.removeStart(rootPathName, "/");
		}
		return rootPathName + "/" + DatetimeUtil.formatDate(new Date(), "yyyy-MM/dd/HH") + "/" + fileName;
	}

	@Override
	public String getFileFullPath(String fileRelativePath) {
		// 阿里云不需要做特殊处理
		return fileRelativePath;
	}

	@Override
	public String getFileFullUrl(String fileRelativePath) {
		String fileUrlPrefix = uploadParamProperties.getAliyunOssBucketUrl();
		Object aliyunFileUrlPrefixObject = sysParamService.findOneValueByParamCode("url.aliyunFileUrlPrefix.string");
		if (null != aliyunFileUrlPrefixObject) {
			// 如果有通过系统参数设置 url 前缀则使用
			fileUrlPrefix = (String) aliyunFileUrlPrefixObject;
		}

		return fileUrlPrefix + "/" + getFileFullPath(fileRelativePath);
	}

	@Override
	public void upload(MultipartFile file, String fileFullPath, String fileRelativePath) {
		String aliyunOssBucketName = uploadParamProperties.getAliyunOssBucketName();

		OSS aliyunOssClient = getAliyunOssClient();
		try {
			PutObjectResult res = aliyunOssClient.putObject(aliyunOssBucketName, fileFullPath, file.getInputStream());
			if (res == null) {
				throw new BusinessException("上传文件失败");
			}
		} catch (Exception e) {
			throw new BusinessException("上传文件失败" + e.getMessage());
		} finally {
			aliyunOssClient.shutdown();
		}
	}

	@Override
	public void uploadByAsync(MultipartFile file, String uuidFileName, String fileFullPath, Long userId, Long bizId) {
		File fileObject = FileUtil.multipartToFile(file, uuidFileName);
		String aliyunOssBucketName = uploadParamProperties.getAliyunOssBucketName();
		OSS aliyunOssClient = getAliyunOssClient();

		try {
			UploadFileRequest uploadFileRequest = new UploadFileRequest(aliyunOssBucketName, fileFullPath);
			uploadFileRequest.setUploadFile(fileObject.getPath());// 指定上传的本地文件
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
			callback.addCallbackVar("x:var2", String.valueOf(bizId));
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

	// =====================================业务处理 end=====================================
	// =====================================私有方法 start=====================================

	private OSS getAliyunOssClient() {
		return new OSSClientBuilder().build(uploadParamProperties.getAliyunOssEndpointUrl(), uploadParamProperties.getAliyunOssAccessKeyId(), uploadParamProperties.getAliyunOssAccessKeySecret());
	}

	// =====================================私有方法 end=====================================

}
