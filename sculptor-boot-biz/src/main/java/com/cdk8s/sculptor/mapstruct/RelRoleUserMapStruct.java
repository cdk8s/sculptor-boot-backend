/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：RelRoleUserMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.relroleuser.*;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relroleuser.*;
import com.cdk8s.sculptor.pojo.bo.service.relroleuser.bases.RelRoleUserBatchCreateServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.relroleuser.*;
import com.cdk8s.sculptor.pojo.dto.response.relroleuser.RelRoleUserResponseDTO;
import com.cdk8s.sculptor.pojo.entity.RelRoleUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface RelRoleUserMapStruct {

	@Mappings({
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getCreateUserId()))"),
	})
	RelRoleUserResponseDTO toResponseDTO(RelRoleUser source);

	List<RelRoleUserResponseDTO> toResponseDTOList(List<RelRoleUser> source);

	//=================================================================================

	RelRoleUserPageQueryServiceBO pageQueryParamToServiceBO(RelRoleUserPageQueryParam source);


	RelRoleUserRoleIdServiceBO roleIdRequestParamToServiceBO(RelRoleUserRoleIdRequestParam source);

	RelRoleUserRoleIdMapperBO roleIdServiceBOToMapperBO(RelRoleUserRoleIdServiceBO source);

	RelRoleUserRoleIdListServiceBO roleIdListRequestParamToServiceBO(RelRoleUserRoleIdListRequestParam source);

	RelRoleUserRoleIdListMapperBO roleIdListServiceBOToMapperBO(RelRoleUserRoleIdListServiceBO source);

	RelRoleUserRoleIdListToDeleteServiceBO roleIdListToDeleteRequestParamToServiceBO(RelRoleUserRoleIdListToDeleteRequestParam source);

	RelRoleUserRoleIdListToDeleteMapperBO roleIdListToDeleteServiceBOToMapperBO(RelRoleUserRoleIdListToDeleteServiceBO source);

	RelRoleUserUserIdServiceBO userIdRequestParamToServiceBO(RelRoleUserUserIdRequestParam source);

	RelRoleUserUserIdMapperBO userIdServiceBOToMapperBO(RelRoleUserUserIdServiceBO source);

	RelRoleUserUserIdListServiceBO userIdListRequestParamToServiceBO(RelRoleUserUserIdListRequestParam source);

	RelRoleUserUserIdListMapperBO userIdListServiceBOToMapperBO(RelRoleUserUserIdListServiceBO source);

	RelRoleUserUserIdListToDeleteServiceBO userIdListToDeleteRequestParamToServiceBO(RelRoleUserUserIdListToDeleteRequestParam source);

	RelRoleUserUserIdListToDeleteMapperBO userIdListToDeleteServiceBOToMapperBO(RelRoleUserUserIdListToDeleteServiceBO source);


	RelRoleUserCreateServiceBO createRequestParamToServiceBO(RelRoleUserCreateRequestParam source);

	RelRoleUserBatchCreateServiceBO batchCreateRequestParamToServiceBO(RelRoleUserBatchCreateRequestParam source);

	RelRoleUserUpdateServiceBO updateRequestParamToServiceBO(RelRoleUserUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);

	RelRoleUserRoleIdAndUserIdToDeleteServiceBO deleteByRoleIdAndUserIdParamToServiceBO(RelRoleUserRoleIdAndUserIdToDeleteRequestParam source);


	//=================================================================================

	RelRoleUser createServiceBOToEntity(RelRoleUserCreateServiceBO source);

	List<RelRoleUser> createServiceBOListToEntityList(List<RelRoleUserCreateServiceBO> source);

	RelRoleUser updateServiceBOToEntity(RelRoleUserUpdateServiceBO source);

	List<RelRoleUser> updateServiceBOListToEntityList(List<RelRoleUserUpdateServiceBO> source);

	//=================================================================================

	RelRoleUserPageQueryMapperBO pageQueryServiceBOToMapperBO(RelRoleUserPageQueryServiceBO source);


	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	RelRoleUserRoleIdAndUserIdToDeleteMapperBO deleteByRoleIdAndUserIdServiceBOToMapperBO(RelRoleUserRoleIdAndUserIdToDeleteServiceBO source);

	//=================================================================================

}
