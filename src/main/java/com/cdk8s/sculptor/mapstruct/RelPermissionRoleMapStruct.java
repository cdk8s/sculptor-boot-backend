package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.relpermissionrole.RelPermissionRolePageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relpermissionrole.RelPermissionRoleBatchCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relpermissionrole.RelPermissionRoleCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relpermissionrole.RelPermissionRolePageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relpermissionrole.RelPermissionRoleUpdateServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.relpermissionrole.RelPermissionRoleBatchCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.relpermissionrole.RelPermissionRoleCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.relpermissionrole.RelPermissionRolePageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.relpermissionrole.RelPermissionRoleUpdateRequestParam;
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
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getUsernameByUserId(source.getCreateUserId()))"),
	})
	RelPermissionRoleResponseDTO toResponseDTO(RelPermissionRole source);

	List<RelPermissionRoleResponseDTO> toResponseDTOList(List<RelPermissionRole> source);

	//=================================================================================

	RelPermissionRolePageQueryServiceBO pageQueryParamToServiceBO(RelPermissionRolePageQueryParam source);

	@Mappings({
			@Mapping(target = "createDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "createUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
	})
	RelPermissionRoleCreateServiceBO createRequestParamToServiceBO(RelPermissionRoleCreateRequestParam source);

	@Mappings({
			@Mapping(target = "createDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "createUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
	})
	RelPermissionRoleBatchCreateServiceBO batchCreateRequestParamToServiceBO(RelPermissionRoleBatchCreateRequestParam source);

	RelPermissionRoleUpdateServiceBO updateRequestParamToServiceBO(RelPermissionRoleUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);


	//=================================================================================
	@Mappings({
			@Mapping(target = "createDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "createUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
	})
	RelPermissionRole createServiceBOToEntity(RelPermissionRoleCreateServiceBO source);

	List<RelPermissionRole> createServiceBOListToEntityList(List<RelPermissionRoleCreateServiceBO> source);

	RelPermissionRole updateServiceBOToEntity(RelPermissionRoleUpdateServiceBO source);

	List<RelPermissionRole> updateServiceBOListToEntityList(List<RelPermissionRoleUpdateServiceBO> source);

	//=================================================================================

	RelPermissionRolePageQueryMapperBO pageQueryServiceBOToMapperBO(RelPermissionRolePageQueryServiceBO source);


	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
