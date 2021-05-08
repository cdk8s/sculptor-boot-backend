/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysParamMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchUpdateStateMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysparam.*;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysparam.*;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysparam.*;
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
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getCreateUserId()))"),
			@Mapping(target = "updateUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getUpdateUserId()))"),
	})
	SysParamResponseDTO toResponseDTO(SysParam source);

	List<SysParamResponseDTO> toResponseDTOList(List<SysParam> source);

	//=================================================================================

	SysParamPageQueryServiceBO pageQueryParamToServiceBO(SysParamPageQueryParam source);

	SysParamParamCodeServiceBO paramCodeRequestParamToServiceBO(SysParamParamCodeRequestParam source);

	SysParamParamCodeMapperBO paramCodeServiceBOToMapperBO(SysParamParamCodeServiceBO source);

	SysParamTypeCodeServiceBO typeCodeRequestParamToServiceBO(SysParamTypeCodeRequestParam source);

	SysParamTypeCodeMapperBO typeCodeServiceBOToMapperBO(SysParamTypeCodeServiceBO source);


	SysParamParamCodeListServiceBO paramCodeListRequestParamToServiceBO(SysParamParamCodeListRequestParam source);

	SysParamParamCodeListMapperBO paramCodeListServiceBOToMapperBO(SysParamParamCodeListServiceBO source);

	SysParamTypeCodeListServiceBO typeCodeListRequestParamToServiceBO(SysParamTypeCodeListRequestParam source);

	SysParamTypeCodeListMapperBO typeCodeListServiceBOToMapperBO(SysParamTypeCodeListServiceBO source);


	SysParamTypeIdServiceBO typeIdRequestParamToServiceBO(SysParamTypeIdRequestParam source);

	SysParamTypeIdMapperBO typeIdServiceBOToMapperBO(SysParamTypeIdServiceBO source);

	SysParamTypeIdListServiceBO typeIdListRequestParamToServiceBO(SysParamTypeIdListRequestParam source);

	SysParamTypeIdListMapperBO typeIdListServiceBOToMapperBO(SysParamTypeIdListServiceBO source);

	SysParamTypeIdListToDeleteServiceBO typeIdListToDeleteRequestParamToServiceBO(SysParamTypeIdListToDeleteRequestParam source);

	SysParamTypeIdListToDeleteMapperBO typeIdListToDeleteServiceBOToMapperBO(SysParamTypeIdListToDeleteServiceBO source);


	SysParamCreateServiceBO createRequestParamToServiceBO(SysParamCreateRequestParam source);


	SysParamUpdateServiceBO updateRequestParamToServiceBO(SysParamUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);

	BatchUpdateStateServiceBO batchUpdateStateParamToServiceBO(BatchUpdateStateRequestParam source);

	//=================================================================================

	SysParam createServiceBOToEntity(SysParamCreateServiceBO source);

	List<SysParam> createServiceBOListToEntityList(List<SysParamCreateServiceBO> source);

	SysParam updateServiceBOToEntity(SysParamUpdateServiceBO source);

	List<SysParam> updateServiceBOListToEntityList(List<SysParamUpdateServiceBO> source);

	//=================================================================================

	SysParamPageQueryMapperBO pageQueryServiceBOToMapperBO(SysParamPageQueryServiceBO source);


	BatchUpdateStateMapperBO batchUpdateStateServiceBOToMapperBO(BatchUpdateStateServiceBO source);

	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
