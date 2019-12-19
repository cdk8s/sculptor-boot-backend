package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.reldeptuser.RelDeptUserPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.reldeptuser.RelDeptUserBatchCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.reldeptuser.RelDeptUserCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.reldeptuser.RelDeptUserPageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.reldeptuser.RelDeptUserUpdateServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.reldeptuser.RelDeptUserBatchCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.reldeptuser.RelDeptUserCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.reldeptuser.RelDeptUserPageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.reldeptuser.RelDeptUserUpdateRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.reldeptuser.RelDeptUserResponseDTO;
import com.cdk8s.sculptor.pojo.entity.RelDeptUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface RelDeptUserMapStruct {

	@Mappings({
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getUsernameByUserId(source.getCreateUserId()))"),
	})
	RelDeptUserResponseDTO toResponseDTO(RelDeptUser source);

	List<RelDeptUserResponseDTO> toResponseDTOList(List<RelDeptUser> source);

	//=================================================================================

	RelDeptUserPageQueryServiceBO pageQueryParamToServiceBO(RelDeptUserPageQueryParam source);

	@Mappings({
			@Mapping(target = "createDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "createUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
	})
	RelDeptUserCreateServiceBO createRequestParamToServiceBO(RelDeptUserCreateRequestParam source);

	@Mappings({
			@Mapping(target = "createDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "createUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
	})
	RelDeptUserBatchCreateServiceBO batchCreateRequestParamToServiceBO(RelDeptUserBatchCreateRequestParam source);

	RelDeptUserUpdateServiceBO updateRequestParamToServiceBO(RelDeptUserUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);


	//=================================================================================
	@Mappings({
			@Mapping(target = "createDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "createUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
	})
	RelDeptUser createServiceBOToEntity(RelDeptUserCreateServiceBO source);

	List<RelDeptUser> createServiceBOListToEntityList(List<RelDeptUserCreateServiceBO> source);

	RelDeptUser updateServiceBOToEntity(RelDeptUserUpdateServiceBO source);

	List<RelDeptUser> updateServiceBOListToEntityList(List<RelDeptUserUpdateServiceBO> source);

	//=================================================================================

	RelDeptUserPageQueryMapperBO pageQueryServiceBOToMapperBO(RelDeptUserPageQueryServiceBO source);


	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
