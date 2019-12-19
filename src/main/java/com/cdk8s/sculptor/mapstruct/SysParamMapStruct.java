package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchUpdateStateMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysparam.SysParamPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysparam.SysParamCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysparam.SysParamPageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysparam.SysParamUpdateServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysparam.SysParamCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysparam.SysParamPageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.sysparam.SysParamUpdateRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.sysparam.SysParamResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface SysParamMapStruct {

	@Mappings({
			@Mapping(target = "paramValueTypeEnumString", expression = "java(com.cdk8s.sculptor.enums.ParamValueTypeEnum.getDescriptionByCode(source.getParamValueTypeEnum()))"),
			@Mapping(target = "stateEnumString", expression = "java(com.cdk8s.sculptor.enums.StateEnum.getDescriptionByCode(source.getStateEnum()))"),
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getUsernameByUserId(source.getCreateUserId()))"),
			@Mapping(target = "updateUsername", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getUsernameByUserId(source.getUpdateUserId()))"),
	})
	SysParamResponseDTO toResponseDTO(SysParam source);

	List<SysParamResponseDTO> toResponseDTOList(List<SysParam> source);

	//=================================================================================

	SysParamPageQueryServiceBO pageQueryParamToServiceBO(SysParamPageQueryParam source);

	@Mappings({
			@Mapping(target = "deleteEnum", expression = "java(com.cdk8s.sculptor.enums.DeleteEnum.NOT_DELETED.getCode())"),
			@Mapping(target = "createDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "createUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
			@Mapping(target = "updateDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "updateUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
	})
	SysParamCreateServiceBO createRequestParamToServiceBO(SysParamCreateRequestParam source);

	@Mappings({
			@Mapping(target = "updateDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "updateUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
	})
	SysParamUpdateServiceBO updateRequestParamToServiceBO(SysParamUpdateRequestParam source);

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

	SysParam createServiceBOToEntity(SysParamCreateServiceBO source);

	List<SysParam> createServiceBOListToEntityList(List<SysParamCreateServiceBO> source);

	SysParam updateServiceBOToEntity(SysParamUpdateServiceBO source);

	List<SysParam> updateServiceBOListToEntityList(List<SysParamUpdateServiceBO> source);

	//=================================================================================

	SysParamPageQueryMapperBO pageQueryServiceBOToMapperBO(SysParamPageQueryServiceBO source);

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
