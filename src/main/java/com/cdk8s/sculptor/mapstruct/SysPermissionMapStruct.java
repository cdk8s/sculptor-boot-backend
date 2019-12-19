package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchUpdateStateMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.syspermission.SysPermissionPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syspermission.SysPermissionCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syspermission.SysPermissionPageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syspermission.SysPermissionUpdateServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.syspermission.SysPermissionCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.syspermission.SysPermissionPageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.syspermission.SysPermissionUpdateRequestParam;
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
			@Mapping(target = "permissionTypeEnumString", expression = "java(com.cdk8s.sculptor.enums.PermissionTypeEnum.getDescriptionByCode(source.getPermissionTypeEnum()))"),
			@Mapping(target = "visibleEnumString", expression = "java(com.cdk8s.sculptor.enums.VisibleEnum.getDescriptionByCode(source.getVisibleEnum()))"),
			@Mapping(target = "boolExtLinkEnumString", expression = "java(com.cdk8s.sculptor.enums.BooleanEnum.getDescriptionByCode(source.getBoolExtLinkEnum()))"),
			@Mapping(target = "boolNewTabEnumString", expression = "java(com.cdk8s.sculptor.enums.BooleanEnum.getDescriptionByCode(source.getBoolNewTabEnum()))"),
			@Mapping(target = "stateEnumString", expression = "java(com.cdk8s.sculptor.enums.StateEnum.getDescriptionByCode(source.getStateEnum()))"),
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getUsernameByUserId(source.getCreateUserId()))"),
			@Mapping(target = "updateUsername", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getUsernameByUserId(source.getUpdateUserId()))"),
	})
	SysPermissionResponseDTO toResponseDTO(SysPermission source);

	List<SysPermissionResponseDTO> toResponseDTOList(List<SysPermission> source);

	//=================================================================================

	SysPermissionPageQueryServiceBO pageQueryParamToServiceBO(SysPermissionPageQueryParam source);

	@Mappings({
			@Mapping(target = "deleteEnum", expression = "java(com.cdk8s.sculptor.enums.DeleteEnum.NOT_DELETED.getCode())"),
			@Mapping(target = "createDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "createUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
			@Mapping(target = "updateDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "updateUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
	})
	SysPermissionCreateServiceBO createRequestParamToServiceBO(SysPermissionCreateRequestParam source);

	@Mappings({
			@Mapping(target = "updateDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "updateUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
	})
	SysPermissionUpdateServiceBO updateRequestParamToServiceBO(SysPermissionUpdateRequestParam source);

	@Mappings({
			@Mapping(target = "deleteUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
			@Mapping(target = "updateUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
	})
	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);

	@Mappings({
			@Mapping(target = "updateUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
	})
	BatchUpdateStateServiceBO batchUpdateStateParamToServiceBO(BatchUpdateStateRequestParam source);

	//=================================================================================
	SysPermission createServiceBOToEntity(SysPermissionCreateServiceBO source);

	List<SysPermission> createServiceBOListToEntityList(List<SysPermissionCreateServiceBO> source);

	SysPermission updateServiceBOToEntity(SysPermissionUpdateServiceBO source);

	List<SysPermission> updateServiceBOListToEntityList(List<SysPermissionUpdateServiceBO> source);

	//=================================================================================

	SysPermissionPageQueryMapperBO pageQueryServiceBOToMapperBO(SysPermissionPageQueryServiceBO source);

	@Mappings({
			@Mapping(target = "updateDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
	})
	BatchUpdateStateMapperBO batchUpdateStateServiceBOToMapperBO(BatchUpdateStateServiceBO source);

	@Mappings({
			@Mapping(target = "deleteDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "updateDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
	})
	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
