package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchUpdateStateMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysdictitem.SysDictItemPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysdictitem.SysDictItemCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysdictitem.SysDictItemPageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysdictitem.SysDictItemUpdateServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysdictitem.SysDictItemCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysdictitem.SysDictItemPageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.sysdictitem.SysDictItemUpdateRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.sysdictitem.SysDictItemResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysDictItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface SysDictItemMapStruct {

	@Mappings({
			@Mapping(target = "dictValueTypeEnumString", expression = "java(com.cdk8s.sculptor.enums.DictValueTypeEnum.getDescriptionByCode(source.getDictValueTypeEnum()))"),
			@Mapping(target = "stateEnumString", expression = "java(com.cdk8s.sculptor.enums.StateEnum.getDescriptionByCode(source.getStateEnum()))"),
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getUsernameByUserId(source.getCreateUserId()))"),
			@Mapping(target = "updateUsername", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getUsernameByUserId(source.getUpdateUserId()))"),
	})
	SysDictItemResponseDTO toResponseDTO(SysDictItem source);

	List<SysDictItemResponseDTO> toResponseDTOList(List<SysDictItem> source);

	//=================================================================================

	SysDictItemPageQueryServiceBO pageQueryParamToServiceBO(SysDictItemPageQueryParam source);

	@Mappings({
			@Mapping(target = "deleteEnum", expression = "java(com.cdk8s.sculptor.enums.DeleteEnum.NOT_DELETED.getCode())"),
			@Mapping(target = "createDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "createUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
			@Mapping(target = "updateDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "updateUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
	})
	SysDictItemCreateServiceBO createRequestParamToServiceBO(SysDictItemCreateRequestParam source);

	@Mappings({
			@Mapping(target = "updateDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "updateUserId", expression = "java(com.cdk8s.sculptor.util.UserInfoContext.getCurrentUserId())"),
	})
	SysDictItemUpdateServiceBO updateRequestParamToServiceBO(SysDictItemUpdateRequestParam source);

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

	SysDictItem createServiceBOToEntity(SysDictItemCreateServiceBO source);

	List<SysDictItem> createServiceBOListToEntityList(List<SysDictItemCreateServiceBO> source);

	SysDictItem updateServiceBOToEntity(SysDictItemUpdateServiceBO source);

	List<SysDictItem> updateServiceBOListToEntityList(List<SysDictItemUpdateServiceBO> source);

	//=================================================================================

	SysDictItemPageQueryMapperBO pageQueryServiceBOToMapperBO(SysDictItemPageQueryServiceBO source);

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
