/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysFileInfoController.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.controller;

import com.cdk8s.sculptor.aop.permission.RequestPermission;
import com.cdk8s.sculptor.controller.bases.SysFileInfoControllerBase;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.mapstruct.SysFileInfoMapStruct;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysfileinfo.SysFileInfoFolderIdServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.sysfileinfo.SysFileInfoFolderIdRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysfileinfo.SysFileInfoPageQueryParam;
import com.cdk8s.sculptor.pojo.dto.response.bases.SelectStringValueLabelDTO;
import com.cdk8s.sculptor.pojo.dto.response.sysfileinfo.SysFileInfoResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysFileInfo;
import com.cdk8s.sculptor.properties.UploadParamProperties;
import com.cdk8s.sculptor.service.SysFileInfoService;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.FileUtil;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.response.biz.R;
import com.github.pagehelper.PageInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/sysFileInfo")
public class SysFileInfoController extends SysFileInfoControllerBase {

	@Autowired
	private SysFileInfoService sysFileInfoService;

	@Autowired
	private SysFileInfoMapStruct sysFileInfoMapStruct;

	@Autowired
	private UploadParamProperties uploadParamProperties;

	// =====================================查询业务 start=====================================

	@RequestPermission("sys_file_info")
	@ResponseBody
	@RequestMapping(value = "/listByFolderId", method = RequestMethod.POST)
	public ResponseEntity<?> listByFolderId(@Valid @RequestBody SysFileInfoFolderIdRequestParam param) {
		SysFileInfoFolderIdServiceBO serviceBO = sysFileInfoMapStruct.folderIdRequestParamToServiceBO(param);
		return R.success(sysFileInfoMapStruct.toResponseDTOList(sysFileInfoService.findListByFolderId(serviceBO)));
	}


	/**
	 * 返回数据库已存储不同的文件后缀
	 */
	@ResponseBody
	@RequestMapping(value = "/distinctFileSuffixList", method = RequestMethod.POST)
	public ResponseEntity<?> distinctFileSuffixList() {
		List<SysFileInfo> distinctFileSuffixList = sysFileInfoService.findDistinctFileSuffixList();

		List<SelectStringValueLabelDTO> dtoList = new ArrayList<>();

		if (CollectionUtil.isNotEmpty(distinctFileSuffixList)) {
			for (SysFileInfo entity : distinctFileSuffixList) {
				String fileSuffix = entity.getFileSuffix();
				if (StringUtil.isNotBlank(fileSuffix)) {
					SelectStringValueLabelDTO itemDTO = new SelectStringValueLabelDTO();
					itemDTO.setValue(fileSuffix);
					itemDTO.setLabel(fileSuffix);
					dtoList.add(itemDTO);
				}
			}
		}

		return R.success(dtoList);
	}

	@RequestPermission("sys_file_info")
	@ResponseBody
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysFileInfoPageQueryParam param) {
		PageInfo result = sysFileInfoService.findPageByServiceBO(sysFileInfoMapStruct.pageQueryParamToServiceBO(param));

		List<SysFileInfo> resultList = result.getList();
		// String watermarkParams = uploadService.getWatermarkParams();
		// String thumbnailParams = uploadService.getThumbnailParams();
		if (CollectionUtil.isNotEmpty(resultList)) {
			for (SysFileInfo entity : resultList) {
				if (FileUtil.checkFileTypeByImage(entity.getFileStorageName())) {
					// entity.setFileFullUrlByThumbnail(entity.getFileFullUrl() + thumbnailParams);
					// entity.setFileFullUrlByWatermark(entity.getFileFullUrl() + watermarkParams);
					entity.setFileFullUrlByThumbnail(entity.getFileFullUrl());
					entity.setFileFullUrlByWatermark(entity.getFileFullUrl());
				} else {
					if (FileUtil.checkFileTypeByVideo(entity.getFileStorageName())) {
						entity.setFileFullUrlByThumbnail("http://icons.veryicon.com/png/System/Min/Video.png");
						entity.setFileFullUrlByWatermark("http://icons.veryicon.com/png/System/Min/Video.png");
					} else {
						entity.setFileFullUrlByThumbnail("http://icons.veryicon.com/png/System/Project%201/File.png");
						entity.setFileFullUrlByWatermark("http://icons.veryicon.com/png/System/Project%201/File.png");
					}
				}
				entity.setFileSizeWithUnit(cn.hutool.core.io.FileUtil.readableFileSize(entity.getFileSize()));
			}
		}

		result.setList(sysFileInfoMapStruct.toResponseDTOList(resultList));
		return R.success(result);
	}

	/**
	 * 文件下载需要用 GET 请求
	 * zchtodo 这里还缺少一个临时下载专用的 token 参数，用于文件下载。
	 */
	@SneakyThrows
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(@RequestParam Long id, HttpServletResponse response) {
		// 暂时只能下载一个文件
		IdServiceBO idServiceBO = new IdServiceBO();
		idServiceBO.setId(id);
		idServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		SysFileInfoResponseDTO dto = sysFileInfoMapStruct.toResponseDTO(sysFileInfoService.findOneById(idServiceBO));
		String filePath = dto.getFileStoragePath() + "/" + dto.getFileStorageName();

		String aliyunOssBucketName = uploadParamProperties.getAliyunOssBucketName();
		// aliyunOssService.downloadFile(aliyunOssBucketName, filePath, response, dto);
	}


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
