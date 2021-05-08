/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：RelPermissionRoleMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.relpermissionrole.*;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relpermissionrole.*;
import com.cdk8s.sculptor.pojo.bo.service.relpermissionrole.bases.RelPermissionRoleBatchCreateServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.relpermissionrole.*;
import com.cdk8s.sculptor.pojo.dto.response.relpermissionrole.RelPermissionRoleResponseDTO;
import com.cdk8s.sculptor.pojo.entity.RelPermissionRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface RelPermissionRoleMapStruct {

	@Mappings({
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getCreateUserId()))"),
	})
	RelPermissionRoleResponseDTO toResponseDTO(RelPermissionRole source);

	List<RelPermissionRoleResponseDTO> toResponseDTOList(List<RelPermissionRole> source);

	//=================================================================================

	RelPermissionRolePageQueryServiceBO pageQueryParamToServiceBO(RelPermissionRolePageQueryParam source);


	RelPermissionRolePermissionIdServiceBO permissionIdRequestParamToServiceBO(RelPermissionRolePermissionIdRequestParam source);

	RelPermissionRolePermissionIdMapperBO permissionIdServiceBOToMapperBO(RelPermissionRolePermissionIdServiceBO source);

	RelPermissionRolePermissionIdListServiceBO permissionIdListRequestParamToServiceBO(RelPermissionRolePermissionIdListRequestParam source);

	RelPermissionRolePermissionIdListMapperBO permissionIdListServiceBOToMapperBO(RelPermissionRolePermissionIdListServiceBO source);

	RelPermissionRolePermissionIdListToDeleteServiceBO permissionIdListToDeleteRequestParamToServiceBO(RelPermissionRolePermissionIdListToDeleteRequestParam source);

	RelPermissionRolePermissionIdListToDeleteMapperBO permissionIdListToDeleteServiceBOToMapperBO(RelPermissionRolePermissionIdListToDeleteServiceBO source);

	RelPermissionRoleRoleIdServiceBO roleIdRequestParamToServiceBO(RelPermissionRoleRoleIdRequestParam source);

	RelPermissionRoleRoleIdMapperBO roleIdServiceBOToMapperBO(RelPermissionRoleRoleIdServiceBO source);

	RelPermissionRoleRoleIdListServiceBO roleIdListRequestParamToServiceBO(RelPermissionRoleRoleIdListRequestParam source);

	RelPermissionRoleRoleIdListMapperBO roleIdListServiceBOToMapperBO(RelPermissionRoleRoleIdListServiceBO source);

	RelPermissionRoleRoleIdListToDeleteServiceBO roleIdListToDeleteRequestParamToServiceBO(RelPermissionRoleRoleIdListToDeleteRequestParam source);

	RelPermissionRoleRoleIdListToDeleteMapperBO roleIdListToDeleteServiceBOToMapperBO(RelPermissionRoleRoleIdListToDeleteServiceBO source);


	RelPermissionRoleCreateServiceBO createRequestParamToServiceBO(RelPermissionRoleCreateRequestParam source);

	RelPermissionRoleBatchCreateServiceBO batchCreateRequestParamToServiceBO(RelPermissionRoleBatchCreateRequestParam source);

	RelPermissionRoleUpdateServiceBO updateRequestParamToServiceBO(RelPermissionRoleUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);

	RelPermissionRolePermissionIdAndRoleIdToDeleteServiceBO deleteByPermissionIdAndRoleIdParamToServiceBO(RelPermissionRolePermissionIdAndRoleIdToDeleteRequestParam source);


	//=================================================================================

	RelPermissionRole createServiceBOToEntity(RelPermissionRoleCreateServiceBO source);

	List<RelPermissionRole> createServiceBOListToEntityList(List<RelPermissionRoleCreateServiceBO> source);

	RelPermissionRole updateServiceBOToEntity(RelPermissionRoleUpdateServiceBO source);

	List<RelPermissionRole> updateServiceBOListToEntityList(List<RelPermissionRoleUpdateServiceBO> source);

	//=================================================================================

	RelPermissionRolePageQueryMapperBO pageQueryServiceBOToMapperBO(RelPermissionRolePageQueryServiceBO source);


	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	RelPermissionRolePermissionIdAndRoleIdToDeleteMapperBO deleteByPermissionIdAndRoleIdServiceBOToMapperBO(RelPermissionRolePermissionIdAndRoleIdToDeleteServiceBO source);
	//=================================================================================

}
