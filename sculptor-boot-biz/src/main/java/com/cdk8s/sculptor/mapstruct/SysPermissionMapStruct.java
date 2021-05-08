/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysPermissionMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchUpdateStateMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.syspermission.SysPermissionPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.syspermission.SysPermissionPermissionCodeListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.syspermission.SysPermissionPermissionCodeMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syspermission.*;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.syspermission.*;
import com.cdk8s.sculptor.pojo.dto.response.syspermission.SysPermissionResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysPermission;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface SysPermissionMapStruct {

	@Mappings({
			@Mapping(target = "belongTypeEnumString", expression = "java(com.cdk8s.sculptor.enums.BelongTypeEnum.getDescriptionByCode(source.getBelongTypeEnum()))"),
			@Mapping(target = "permissionTypeEnumString", expression = "java(com.cdk8s.sculptor.enums.PermissionTypeEnum.getDescriptionByCode(source.getPermissionTypeEnum()))"),
			@Mapping(target = "visibleEnumString", expression = "java(com.cdk8s.sculptor.enums.VisibleEnum.getDescriptionByCode(source.getVisibleEnum()))"),
			@Mapping(target = "boolExtLinkEnumString", expression = "java(com.cdk8s.sculptor.enums.BooleanEnum.getDescriptionByCode(source.getBoolExtLinkEnum()))"),
			@Mapping(target = "boolNewTabEnumString", expression = "java(com.cdk8s.sculptor.enums.BooleanEnum.getDescriptionByCode(source.getBoolNewTabEnum()))"),
			@Mapping(target = "stateEnumString", expression = "java(com.cdk8s.sculptor.enums.StateEnum.getDescriptionByCode(source.getStateEnum()))"),
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getCreateUserId()))"),
			@Mapping(target = "updateUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getUpdateUserId()))"),
	})
	SysPermissionResponseDTO toResponseDTO(SysPermission source);

	List<SysPermissionResponseDTO> toResponseDTOList(List<SysPermission> source);

	//=================================================================================

	SysPermissionPageQueryServiceBO pageQueryParamToServiceBO(SysPermissionPageQueryParam source);

	SysPermissionPermissionCodeServiceBO permissionCodeRequestParamToServiceBO(SysPermissionPermissionCodeRequestParam source);

	SysPermissionPermissionCodeMapperBO permissionCodeServiceBOToMapperBO(SysPermissionPermissionCodeServiceBO source);


	SysPermissionPermissionCodeListServiceBO permissionCodeListRequestParamToServiceBO(SysPermissionPermissionCodeListRequestParam source);

	SysPermissionPermissionCodeListMapperBO permissionCodeListServiceBOToMapperBO(SysPermissionPermissionCodeListServiceBO source);


	SysPermissionCreateServiceBO createRequestParamToServiceBO(SysPermissionCreateRequestParam source);


	SysPermissionUpdateServiceBO updateRequestParamToServiceBO(SysPermissionUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);

	BatchUpdateStateServiceBO batchUpdateStateParamToServiceBO(BatchUpdateStateRequestParam source);

	//=================================================================================

	SysPermission createServiceBOToEntity(SysPermissionCreateServiceBO source);

	List<SysPermission> createServiceBOListToEntityList(List<SysPermissionCreateServiceBO> source);

	SysPermission updateServiceBOToEntity(SysPermissionUpdateServiceBO source);

	List<SysPermission> updateServiceBOListToEntityList(List<SysPermissionUpdateServiceBO> source);

	//=================================================================================

	SysPermissionPageQueryMapperBO pageQueryServiceBOToMapperBO(SysPermissionPageQueryServiceBO source);


	BatchUpdateStateMapperBO batchUpdateStateServiceBOToMapperBO(BatchUpdateStateServiceBO source);

	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
