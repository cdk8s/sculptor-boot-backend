/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysFileInfoMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchUpdateStateMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysfileinfo.*;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysfileinfo.*;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysfileinfo.*;
import com.cdk8s.sculptor.pojo.dto.response.sysfileinfo.SysFileInfoResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysFileInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface SysFileInfoMapStruct {

	@Mappings({
			@Mapping(target = "fileStorageTypeEnumString", expression = "java(com.cdk8s.sculptor.enums.FileStorageTypeEnum.getDescriptionByCode(source.getFileStorageTypeEnum()))"),
			@Mapping(target = "boolTopEnumString", expression = "java(com.cdk8s.sculptor.enums.BooleanEnum.getDescriptionByCode(source.getBoolTopEnum()))"),
			@Mapping(target = "boolOssCompleteEnumString", expression = "java(com.cdk8s.sculptor.enums.BooleanEnum.getDescriptionByCode(source.getBoolOssCompleteEnum()))"),
			@Mapping(target = "boolOssDeleteEnumString", expression = "java(com.cdk8s.sculptor.enums.BooleanEnum.getDescriptionByCode(source.getBoolOssDeleteEnum()))"),
			@Mapping(target = "stateEnumString", expression = "java(com.cdk8s.sculptor.enums.StateEnum.getDescriptionByCode(source.getStateEnum()))"),
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getCreateUserId()))"),
			@Mapping(target = "updateUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getUpdateUserId()))"),
	})
	SysFileInfoResponseDTO toResponseDTO(SysFileInfo source);

	List<SysFileInfoResponseDTO> toResponseDTOList(List<SysFileInfo> source);

	//=================================================================================

	SysFileInfoPageQueryServiceBO pageQueryParamToServiceBO(SysFileInfoPageQueryParam source);

	SysFileInfoFileSuffixServiceBO fileSuffixRequestParamToServiceBO(SysFileInfoFileSuffixRequestParam source);

	SysFileInfoFileSuffixMapperBO fileSuffixServiceBOToMapperBO(SysFileInfoFileSuffixServiceBO source);

	SysFileInfoFileStorageNameServiceBO fileStorageNameRequestParamToServiceBO(SysFileInfoFileStorageNameRequestParam source);

	SysFileInfoFileStorageNameMapperBO fileStorageNameServiceBOToMapperBO(SysFileInfoFileStorageNameServiceBO source);

	SysFileInfoFileShowNameServiceBO fileShowNameRequestParamToServiceBO(SysFileInfoFileShowNameRequestParam source);

	SysFileInfoFileShowNameMapperBO fileShowNameServiceBOToMapperBO(SysFileInfoFileShowNameServiceBO source);


	SysFileInfoFileSuffixListServiceBO fileSuffixListRequestParamToServiceBO(SysFileInfoFileSuffixListRequestParam source);

	SysFileInfoFileSuffixListMapperBO fileSuffixListServiceBOToMapperBO(SysFileInfoFileSuffixListServiceBO source);

	SysFileInfoFileStorageNameListServiceBO fileStorageNameListRequestParamToServiceBO(SysFileInfoFileStorageNameListRequestParam source);

	SysFileInfoFileStorageNameListMapperBO fileStorageNameListServiceBOToMapperBO(SysFileInfoFileStorageNameListServiceBO source);

	SysFileInfoFileShowNameListServiceBO fileShowNameListRequestParamToServiceBO(SysFileInfoFileShowNameListRequestParam source);

	SysFileInfoFileShowNameListMapperBO fileShowNameListServiceBOToMapperBO(SysFileInfoFileShowNameListServiceBO source);


	SysFileInfoFolderIdServiceBO folderIdRequestParamToServiceBO(SysFileInfoFolderIdRequestParam source);

	SysFileInfoFolderIdMapperBO folderIdServiceBOToMapperBO(SysFileInfoFolderIdServiceBO source);

	SysFileInfoFolderIdListServiceBO folderIdListRequestParamToServiceBO(SysFileInfoFolderIdListRequestParam source);

	SysFileInfoFolderIdListMapperBO folderIdListServiceBOToMapperBO(SysFileInfoFolderIdListServiceBO source);

	SysFileInfoFolderIdListToDeleteServiceBO folderIdListToDeleteRequestParamToServiceBO(SysFileInfoFolderIdListToDeleteRequestParam source);

	SysFileInfoFolderIdListToDeleteMapperBO folderIdListToDeleteServiceBOToMapperBO(SysFileInfoFolderIdListToDeleteServiceBO source);


	SysFileInfoCreateServiceBO createRequestParamToServiceBO(SysFileInfoCreateRequestParam source);


	SysFileInfoUpdateServiceBO updateRequestParamToServiceBO(SysFileInfoUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);

	BatchUpdateStateServiceBO batchUpdateStateParamToServiceBO(BatchUpdateStateRequestParam source);

	//=================================================================================

	SysFileInfo createServiceBOToEntity(SysFileInfoCreateServiceBO source);

	List<SysFileInfo> createServiceBOListToEntityList(List<SysFileInfoCreateServiceBO> source);

	SysFileInfo updateServiceBOToEntity(SysFileInfoUpdateServiceBO source);

	List<SysFileInfo> updateServiceBOListToEntityList(List<SysFileInfoUpdateServiceBO> source);

	//=================================================================================

	SysFileInfoPageQueryMapperBO pageQueryServiceBOToMapperBO(SysFileInfoPageQueryServiceBO source);


	BatchUpdateStateMapperBO batchUpdateStateServiceBOToMapperBO(BatchUpdateStateServiceBO source);

	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
