package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchUpdateStateMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysrole.SysRolePageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysrole.SysRoleCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysrole.SysRolePageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysrole.SysRoleUpdateServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysrole.SysRoleCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysrole.SysRolePageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.sysrole.SysRoleUpdateRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.sysrole.SysRoleResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface SysRoleMapStruct {

	@Mappings({
			@Mapping(target = "stateEnumString", expression = "java(com.cdk8s.sculptor.enums.StateEnum.getDescriptionByCode(source.getStateEnum()))"),
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getUsernameByUserId(source.getCreateUserId()))"),
			@Mapping(target = "updateUsername", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getUsernameByUserId(source.getUpdateUserId()))"),
	})
	SysRoleResponseDTO toResponseDTO(SysRole source);

	List<SysRoleResponseDTO> toResponseDTOList(List<SysRole> source);

	//=================================================================================

	SysRolePageQueryServiceBO pageQueryParamToServiceBO(SysRolePageQueryParam source);

	@Mappings({
			@Mapping(target = "deleteEnum", expression = "java(com.cdk8s.sculptor.enums.DeleteEnum.NOT_DELETED.getCode())"),
			@Mapping(target = "createDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "createUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
			@Mapping(target = "updateDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "updateUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
	})
	SysRoleCreateServiceBO createRequestParamToServiceBO(SysRoleCreateRequestParam source);

	@Mappings({
			@Mapping(target = "updateDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "updateUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
	})
	SysRoleUpdateServiceBO updateRequestParamToServiceBO(SysRoleUpdateRequestParam source);

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

	SysRole createServiceBOToEntity(SysRoleCreateServiceBO source);

	List<SysRole> createServiceBOListToEntityList(List<SysRoleCreateServiceBO> source);

	SysRole updateServiceBOToEntity(SysRoleUpdateServiceBO source);

	List<SysRole> updateServiceBOListToEntityList(List<SysRoleUpdateServiceBO> source);

	//=================================================================================

	SysRolePageQueryMapperBO pageQueryServiceBOToMapperBO(SysRolePageQueryServiceBO source);

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
