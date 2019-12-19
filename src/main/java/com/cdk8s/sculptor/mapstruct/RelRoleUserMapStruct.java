package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.relroleuser.RelRoleUserPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relroleuser.RelRoleUserBatchCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relroleuser.RelRoleUserCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relroleuser.RelRoleUserPageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.relroleuser.RelRoleUserUpdateServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.relroleuser.RelRoleUserBatchCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.relroleuser.RelRoleUserCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.relroleuser.RelRoleUserPageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.relroleuser.RelRoleUserUpdateRequestParam;
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
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getUsernameByUserId(source.getCreateUserId()))"),
	})
	RelRoleUserResponseDTO toResponseDTO(RelRoleUser source);

	List<RelRoleUserResponseDTO> toResponseDTOList(List<RelRoleUser> source);

	//=================================================================================

	RelRoleUserPageQueryServiceBO pageQueryParamToServiceBO(RelRoleUserPageQueryParam source);

	@Mappings({
			@Mapping(target = "createDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "createUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
	})
	RelRoleUserCreateServiceBO createRequestParamToServiceBO(RelRoleUserCreateRequestParam source);

	@Mappings({
			@Mapping(target = "createDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "createUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
	})
	RelRoleUserBatchCreateServiceBO batchCreateRequestParamToServiceBO(RelRoleUserBatchCreateRequestParam source);

	RelRoleUserUpdateServiceBO updateRequestParamToServiceBO(RelRoleUserUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);


	//=================================================================================
	@Mappings({
			@Mapping(target = "createDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "createUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
	})
	RelRoleUser createServiceBOToEntity(RelRoleUserCreateServiceBO source);

	List<RelRoleUser> createServiceBOListToEntityList(List<RelRoleUserCreateServiceBO> source);

	RelRoleUser updateServiceBOToEntity(RelRoleUserUpdateServiceBO source);

	List<RelRoleUser> updateServiceBOListToEntityList(List<RelRoleUserUpdateServiceBO> source);

	//=================================================================================

	RelRoleUserPageQueryMapperBO pageQueryServiceBOToMapperBO(RelRoleUserPageQueryServiceBO source);


	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
