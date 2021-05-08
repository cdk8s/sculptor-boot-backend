/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysFolderInfoMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchUpdateStateMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysfolderinfo.SysFolderInfoFolderNameListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysfolderinfo.SysFolderInfoFolderNameMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysfolderinfo.SysFolderInfoPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysfolderinfo.*;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysfolderinfo.*;
import com.cdk8s.sculptor.pojo.dto.response.sysfolderinfo.SysFolderInfoResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysFolderInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface SysFolderInfoMapStruct {

	@Mappings({
			@Mapping(target = "boolTopEnumString", expression = "java(com.cdk8s.sculptor.enums.BooleanEnum.getDescriptionByCode(source.getBoolTopEnum()))"),
			@Mapping(target = "stateEnumString", expression = "java(com.cdk8s.sculptor.enums.StateEnum.getDescriptionByCode(source.getStateEnum()))"),
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getCreateUserId()))"),
			@Mapping(target = "updateUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getUpdateUserId()))"),
	})
	SysFolderInfoResponseDTO toResponseDTO(SysFolderInfo source);

	List<SysFolderInfoResponseDTO> toResponseDTOList(List<SysFolderInfo> source);

	//=================================================================================

	SysFolderInfoPageQueryServiceBO pageQueryParamToServiceBO(SysFolderInfoPageQueryParam source);

	SysFolderInfoFolderNameServiceBO folderNameRequestParamToServiceBO(SysFolderInfoFolderNameRequestParam source);

	SysFolderInfoFolderNameMapperBO folderNameServiceBOToMapperBO(SysFolderInfoFolderNameServiceBO source);


	SysFolderInfoFolderNameListServiceBO folderNameListRequestParamToServiceBO(SysFolderInfoFolderNameListRequestParam source);

	SysFolderInfoFolderNameListMapperBO folderNameListServiceBOToMapperBO(SysFolderInfoFolderNameListServiceBO source);


	SysFolderInfoCreateServiceBO createRequestParamToServiceBO(SysFolderInfoCreateRequestParam source);


	SysFolderInfoUpdateServiceBO updateRequestParamToServiceBO(SysFolderInfoUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);

	BatchUpdateStateServiceBO batchUpdateStateParamToServiceBO(BatchUpdateStateRequestParam source);

	//=================================================================================

	SysFolderInfo createServiceBOToEntity(SysFolderInfoCreateServiceBO source);

	List<SysFolderInfo> createServiceBOListToEntityList(List<SysFolderInfoCreateServiceBO> source);

	SysFolderInfo updateServiceBOToEntity(SysFolderInfoUpdateServiceBO source);

	List<SysFolderInfo> updateServiceBOListToEntityList(List<SysFolderInfoUpdateServiceBO> source);

	//=================================================================================

	SysFolderInfoPageQueryMapperBO pageQueryServiceBOToMapperBO(SysFolderInfoPageQueryServiceBO source);


	BatchUpdateStateMapperBO batchUpdateStateServiceBOToMapperBO(BatchUpdateStateServiceBO source);

	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
