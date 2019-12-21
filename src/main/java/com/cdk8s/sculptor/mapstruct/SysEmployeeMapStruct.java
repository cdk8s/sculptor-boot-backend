package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysemployee.SysEmployeeBatchDeleteByUserIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysemployee.SysEmployeePageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysemployee.SysEmployeeBatchDeleteByUserIdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysemployee.SysEmployeeCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysemployee.SysEmployeePageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysemployee.SysEmployeeUpdateServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysemployee.SysEmployeeCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysemployee.SysEmployeePageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.sysemployee.SysEmployeeUpdateRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.sysemployee.SysEmployeeResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysEmployee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface SysEmployeeMapStruct {

	@Mappings({
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getUsernameByUserId(source.getCreateUserId()))"),
			@Mapping(target = "updateUsername", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getUsernameByUserId(source.getUpdateUserId()))"),
	})
	SysEmployeeResponseDTO toResponseDTO(SysEmployee source);

	List<SysEmployeeResponseDTO> toResponseDTOList(List<SysEmployee> source);

	//=================================================================================

	SysEmployeePageQueryServiceBO pageQueryParamToServiceBO(SysEmployeePageQueryParam source);

	@Mappings({
			@Mapping(target = "deleteEnum", expression = "java(com.cdk8s.sculptor.enums.DeleteEnum.NOT_DELETED.getCode())"),
			@Mapping(target = "createDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "createUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
			@Mapping(target = "updateDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "updateUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
	})
	SysEmployeeCreateServiceBO createRequestParamToServiceBO(SysEmployeeCreateRequestParam source);

	@Mappings({
			@Mapping(target = "updateDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "updateUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
	})
	SysEmployeeUpdateServiceBO updateRequestParamToServiceBO(SysEmployeeUpdateRequestParam source);

	@Mappings({
			@Mapping(target = "deleteUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
			@Mapping(target = "updateUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
	})
	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);


	//=================================================================================

	@Mappings({
			@Mapping(target = "deleteEnum", expression = "java(com.cdk8s.sculptor.enums.DeleteEnum.NOT_DELETED.getCode())"),
			@Mapping(target = "createDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "createUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
			@Mapping(target = "updateDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "updateUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
	})
	SysEmployee createServiceBOToEntity(SysEmployeeCreateServiceBO source);

	List<SysEmployee> createServiceBOListToEntityList(List<SysEmployeeCreateServiceBO> source);

	@Mappings({
			@Mapping(target = "updateDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "updateUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
	})
	SysEmployee updateServiceBOToEntity(SysEmployeeUpdateServiceBO source);

	List<SysEmployee> updateServiceBOListToEntityList(List<SysEmployeeUpdateServiceBO> source);

	//=================================================================================

	SysEmployeePageQueryMapperBO pageQueryServiceBOToMapperBO(SysEmployeePageQueryServiceBO source);

	SysEmployeeBatchDeleteByUserIdMapperBO batchDeleteByUserIdServiceBOToMapperBO(SysEmployeeBatchDeleteByUserIdServiceBO source);


	@Mappings({
			@Mapping(target = "deleteDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "updateDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
	})
	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
