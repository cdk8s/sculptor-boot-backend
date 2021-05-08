/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysDictItemMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchUpdateStateMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysdictitem.*;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysdictitem.*;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysdictitem.*;
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
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getCreateUserId()))"),
			@Mapping(target = "updateUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getUpdateUserId()))"),
	})
	SysDictItemResponseDTO toResponseDTO(SysDictItem source);

	List<SysDictItemResponseDTO> toResponseDTOList(List<SysDictItem> source);

	//=================================================================================

	SysDictItemPageQueryServiceBO pageQueryParamToServiceBO(SysDictItemPageQueryParam source);

	SysDictItemItemCodeServiceBO itemCodeRequestParamToServiceBO(SysDictItemItemCodeRequestParam source);

	SysDictItemItemCodeMapperBO itemCodeServiceBOToMapperBO(SysDictItemItemCodeServiceBO source);

	SysDictItemDictCodeServiceBO dictCodeRequestParamToServiceBO(SysDictItemDictCodeRequestParam source);

	SysDictItemDictCodeMapperBO dictCodeServiceBOToMapperBO(SysDictItemDictCodeServiceBO source);


	SysDictItemItemCodeListServiceBO itemCodeListRequestParamToServiceBO(SysDictItemItemCodeListRequestParam source);

	SysDictItemItemCodeListMapperBO itemCodeListServiceBOToMapperBO(SysDictItemItemCodeListServiceBO source);

	SysDictItemDictCodeListServiceBO dictCodeListRequestParamToServiceBO(SysDictItemDictCodeListRequestParam source);

	SysDictItemDictCodeListMapperBO dictCodeListServiceBOToMapperBO(SysDictItemDictCodeListServiceBO source);


	SysDictItemDictIdServiceBO dictIdRequestParamToServiceBO(SysDictItemDictIdRequestParam source);

	SysDictItemDictIdMapperBO dictIdServiceBOToMapperBO(SysDictItemDictIdServiceBO source);

	SysDictItemDictIdListServiceBO dictIdListRequestParamToServiceBO(SysDictItemDictIdListRequestParam source);

	SysDictItemDictIdListMapperBO dictIdListServiceBOToMapperBO(SysDictItemDictIdListServiceBO source);

	SysDictItemDictIdListToDeleteServiceBO dictIdListToDeleteRequestParamToServiceBO(SysDictItemDictIdListToDeleteRequestParam source);

	SysDictItemDictIdListToDeleteMapperBO dictIdListToDeleteServiceBOToMapperBO(SysDictItemDictIdListToDeleteServiceBO source);


	SysDictItemCreateServiceBO createRequestParamToServiceBO(SysDictItemCreateRequestParam source);


	SysDictItemUpdateServiceBO updateRequestParamToServiceBO(SysDictItemUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);

	BatchUpdateStateServiceBO batchUpdateStateParamToServiceBO(BatchUpdateStateRequestParam source);

	//=================================================================================

	SysDictItem createServiceBOToEntity(SysDictItemCreateServiceBO source);

	List<SysDictItem> createServiceBOListToEntityList(List<SysDictItemCreateServiceBO> source);

	SysDictItem updateServiceBOToEntity(SysDictItemUpdateServiceBO source);

	List<SysDictItem> updateServiceBOListToEntityList(List<SysDictItemUpdateServiceBO> source);

	//=================================================================================

	SysDictItemPageQueryMapperBO pageQueryServiceBOToMapperBO(SysDictItemPageQueryServiceBO source);


	BatchUpdateStateMapperBO batchUpdateStateServiceBOToMapperBO(BatchUpdateStateServiceBO source);

	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
