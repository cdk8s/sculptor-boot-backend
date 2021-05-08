/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：UploadService.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.enums.UploadOssTypeEnum;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.properties.UploadParamProperties;
import com.cdk8s.sculptor.util.FileUtil;
import com.cdk8s.sculptor.util.StringUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * 该类功能暂时废弃
 */
@Slf4j
@CacheConfig(cacheNames = "UploadService")
@Service
public class UploadService {

	@Autowired
	private UpyunService upyunService;

	@Autowired
	private AliyunOssService aliyunOssService;

	@Autowired
	private UploadParamProperties uploadParamProperties;

	@Autowired
	private SysParamService sysParamService;

	// =====================================查询业务 start=====================================


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public String getFileUrlPrefix() {
		String fileUrlPrefix = null;
		if (uploadParamProperties.getUploadOssTypeEnum() == UploadOssTypeEnum.ALIYUN) {
			fileUrlPrefix = (String) sysParamService.findOneValueByParamCode("url.aliyunFileUrlPrefix.string");
		} else {
			// zchtodo 其他方式补充
		}
		return fileUrlPrefix;
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public String getFilePath(String newFileName, Boolean addDatePathEnable, String folderPath) {
		String filePath = null;
		if (uploadParamProperties.getUploadOssTypeEnum() == UploadOssTypeEnum.ALIYUN) {
			filePath = aliyunOssService.getFilePath(newFileName, addDatePathEnable, folderPath);
		} else {
			// zchtodo 其他方式补充
		}
		return filePath;
	}


	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public String getWatermarkParams() {
		String watermarkParams;
		if (uploadParamProperties.getUploadOssTypeEnum() == UploadOssTypeEnum.ALIYUN) {
			watermarkParams = aliyunOssService.getWatermarkParams();
		} else {
			watermarkParams = upyunService.getWatermarkParams();
		}

		return watermarkParams;
	}

	@Transactional(readOnly = true)
	@Cacheable(keyGenerator = "keyGeneratorToServiceParam")
	public String getThumbnailParams() {
		String thumbnailParams = "";
		if (uploadParamProperties.getUploadOssTypeEnum() == UploadOssTypeEnum.ALIYUN) {
			thumbnailParams = aliyunOssService.getThumbnailParams();
		} else {
			thumbnailParams = upyunService.getThumbnailParams();
		}

		return thumbnailParams;
	}

	// =====================================查询业务 end=====================================

	// =====================================操作业务 start=====================================

	/**
	 * 该方法名不允许修改，被用在反射上
	 */
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public void cacheEvict() {
		// 用来主动清除所有缓存数据
	}

	/**
	 * 异步上传
	 */
	@Async
	@SneakyThrows
	@Transactional(readOnly = true)
	public void uploadByAsync(File file, String filePath, Long userId, Long businessId) {
		// 因为是异步上传，所以相关文件格式校验要前置到前面步骤，这里不做校验
		UploadOssTypeEnum uploadOssTypeEnum = uploadParamProperties.getUploadOssTypeEnum();
		parallelUpload(file, uploadOssTypeEnum, filePath, userId, businessId);
	}

	@SneakyThrows
	@Transactional(readOnly = true)
	public String uploadImage(MultipartFile file, String newFileName, String filePath) {
		checkImageFormat(file);

		String result = upload(file, uploadParamProperties.getUploadOssTypeEnum(), newFileName, filePath);
		if (StringUtil.isBlank(result)) {
			throw new BusinessException("上传失败，没有返回上传后的图片地址");
		}

		return result;
	}


	@SneakyThrows
	@Transactional(readOnly = true)
	public String uploadVideo(MultipartFile file, String newFileName, String filePath) {
		checkVideoFormat(file);
		UploadOssTypeEnum uploadOssTypeEnum = uploadParamProperties.getUploadOssTypeEnum();
		String result = upload(file, uploadOssTypeEnum, newFileName, filePath);
		if (StringUtil.isBlank(result)) {
			throw new BusinessException("上传失败，没有返回上传后的视频地址");
		}

		return result;
	}

	@SneakyThrows
	@Transactional(readOnly = true)
	public String uploadFile(MultipartFile file, String newFileName, String filePath) {
		checkFileFormat(file);

		String result = upload(file, uploadParamProperties.getUploadOssTypeEnum(), newFileName, filePath);
		if (StringUtil.isBlank(result)) {
			throw new BusinessException("上传失败，没有返回上传后的文件地址");
		}

		return result;
	}

	public void checkImageFormat(MultipartFile file) {
		String originalFilename = file.getOriginalFilename();
		if (StringUtil.isBlank(originalFilename)) {
			throw new BusinessException("图片名称格式不正确");
		}

		// 校验文件格式
		if (!FileUtil.checkFileTypeByImage(originalFilename)) {
			throw new BusinessException("只能选择图片类型文件");
		}

		// 校验文件大小，不允许超过 10M，getSize 单位 bytes
		if (file.getSize() > 10485760) {
			throw new BusinessException("图片过大！请上传小图片");
		}
	}

	public void checkVideoFormat(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		if (StringUtil.isBlank(fileName)) {
			throw new BusinessException("视频名称格式不正确");
		}

		// 校验文件格式
		if (!FileUtil.checkFileTypeByVideo(fileName)) {
			throw new BusinessException("只能选择视频类型文件");
		}

		// 校验文件大小，不允许超过 500M，getSize 单位 bytes
		if (file.getSize() > 524288000) {
			throw new BusinessException("视频过大！请上传小于 500M 的视频");
		}
	}

	public void checkFileFormat(MultipartFile file) {
		String fileName = file.getOriginalFilename();
		if (StringUtil.isBlank(fileName)) {
			throw new BusinessException("文件名称格式不正确");
		}

		// 校验文件大小，不允许超过 500M，getSize 单位 bytes
		if (file.getSize() > 524288000) {
			throw new BusinessException("文件过大！请上传小于 500M 的文件");
		}
	}


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private String upload(MultipartFile file, UploadOssTypeEnum uploadOssTypeEnum, String newFileName, String filePath) {
		if (UploadOssTypeEnum.ALIYUN == uploadOssTypeEnum) {
			return aliyunOssService.upload(file, newFileName, filePath);
		}

		// zchtodo 又拍云还要改造
		// return upyunService.upload(files);
		return null;
	}

	private void parallelUpload(File file, UploadOssTypeEnum uploadOssTypeEnum, String filePath, Long userId, Long businessId) {
		if (UploadOssTypeEnum.ALIYUN == uploadOssTypeEnum) {
			aliyunOssService.parallelUpload(file, filePath, userId, businessId);
		}

		// zchtodo 又拍云还要改造
		// return upyunService.upload(files);

	}

	// =====================================私有方法 end=====================================

}

