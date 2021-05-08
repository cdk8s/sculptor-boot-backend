/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：UploadParamProperties.java
 * 项目名称：sculptor-boot-common-properties
 * 项目描述：公共工具
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.properties;

import com.cdk8s.sculptor.enums.UploadOssTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.File;


@Setter
@Getter
@ToString
@Component
@ConfigurationProperties(prefix = "custom.properties.upload")
public class UploadParamProperties {

	private UploadOssTypeEnum uploadOssTypeEnum;

	// 默认值为当前用户 home 目录
	private String localUploadStoragePath = System.getProperty("user.home") + File.separator + "sculptor-boot-backend-upload-dir" + File.separator + "upload";
	private String localUploadFileUrlRelativePath;

	private String upyunBucketName;
	private String upyunUsername;
	private String upyunPassword;

	// 可以在 Bucket 的 overview 页面上看到，区分内外地址
	private String aliyunOssEndpointProtocol;
	private String aliyunOssEndpointUrl;
	private String aliyunOssEndpoint;

	// RAM endpoint 常见地区接入地址说明：https://help.aliyun.com/document_detail/66053.html
	private String aliyunOssStsEndpoint;
	// RAM 中配置得到的角色 ARN 值
	private String aliyunOssStsRoleArn;

	private String aliyunOssBucketName;
	private String aliyunOssRegion;
	private String aliyunOssBucketUrl;

	// 可以在这里生成：https://ram.console.aliyun.com
	private String aliyunOssAccessKeyId;
	private String aliyunOssAccessKeySecret;
	private String aliyunOssCallbackUrl;


}
