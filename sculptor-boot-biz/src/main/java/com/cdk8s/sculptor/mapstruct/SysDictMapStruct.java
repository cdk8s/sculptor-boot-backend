/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysDictMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchUpdateStateMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysdict.SysDictDictCodeListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysdict.SysDictDictCodeMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysdict.SysDictPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysdict.*;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysdict.*;
import com.cdk8s.sculptor.pojo.dto.response.sysdict.SysDictResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysDict;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface SysDictMapStruct {

	@Mappings({
			@Mapping(target = "dictValueTypeEnumString", expression = "java(com.cdk8s.sculptor.enums.DictValueTypeEnum.getDescriptionByCode(source.getDictValueTypeEnum()))"),
			@Mapping(target = "stateEnumString", expression = "java(com.cdk8s.sculptor.enums.StateEnum.getDescriptionByCode(source.getStateEnum()))"),
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getCreateUserId()))"),
			@Mapping(target = "updateUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getUpdateUserId()))"),
	})
	SysDictResponseDTO toResponseDTO(SysDict source);

	List<SysDictResponseDTO> toResponseDTOList(List<SysDict> source);

	//=================================================================================

	SysDictPageQueryServiceBO pageQueryParamToServiceBO(SysDictPageQueryParam source);

	SysDictDictCodeServiceBO dictCodeRequestParamToServiceBO(SysDictDictCodeRequestParam source);

	SysDictDictCodeMapperBO dictCodeServiceBOToMapperBO(SysDictDictCodeServiceBO source);


	SysDictDictCodeListServiceBO dictCodeListRequestParamToServiceBO(SysDictDictCodeListRequestParam source);

	SysDictDictCodeListMapperBO dictCodeListServiceBOToMapperBO(SysDictDictCodeListServiceBO source);


	SysDictCreateServiceBO createRequestParamToServiceBO(SysDictCreateRequestParam source);


	SysDictUpdateServiceBO updateRequestParamToServiceBO(SysDictUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);

	BatchUpdateStateServiceBO batchUpdateStateParamToServiceBO(BatchUpdateStateRequestParam source);

	//=================================================================================

	SysDict createServiceBOToEntity(SysDictCreateServiceBO source);

	List<SysDict> createServiceBOListToEntityList(List<SysDictCreateServiceBO> source);

	SysDict updateServiceBOToEntity(SysDictUpdateServiceBO source);

	List<SysDict> updateServiceBOListToEntityList(List<SysDictUpdateServiceBO> source);

	//=================================================================================

	SysDictPageQueryMapperBO pageQueryServiceBOToMapperBO(SysDictPageQueryServiceBO source);


	BatchUpdateStateMapperBO batchUpdateStateServiceBOToMapperBO(BatchUpdateStateServiceBO source);

	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
