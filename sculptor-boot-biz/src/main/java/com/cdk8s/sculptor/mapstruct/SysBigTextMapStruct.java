/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysBigTextMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchUpdateStateMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysbigtext.SysBigTextPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysbigtext.SysBigTextTextCodeListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysbigtext.SysBigTextTextCodeMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysbigtext.SysBigTextTextTitleLikeQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysbigtext.*;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysbigtext.*;
import com.cdk8s.sculptor.pojo.dto.response.sysbigtext.SysBigTextResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysBigText;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface SysBigTextMapStruct {

	@Mappings({
			@Mapping(target = "stateEnumString", expression = "java(com.cdk8s.sculptor.enums.StateEnum.getDescriptionByCode(source.getStateEnum()))"),
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getCreateUserId()))"),
			@Mapping(target = "updateUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getUpdateUserId()))"),
	})
	SysBigTextResponseDTO toResponseDTO(SysBigText source);

	List<SysBigTextResponseDTO> toResponseDTOList(List<SysBigText> source);

	//=================================================================================

	SysBigTextPageQueryServiceBO pageQueryParamToServiceBO(SysBigTextPageQueryParam source);

	SysBigTextTextCodeServiceBO textCodeRequestParamToServiceBO(SysBigTextTextCodeRequestParam source);

	SysBigTextTextCodeMapperBO textCodeServiceBOToMapperBO(SysBigTextTextCodeServiceBO source);


	SysBigTextTextCodeListServiceBO textCodeListRequestParamToServiceBO(SysBigTextTextCodeListRequestParam source);

	SysBigTextTextCodeListMapperBO textCodeListServiceBOToMapperBO(SysBigTextTextCodeListServiceBO source);


	SysBigTextTextTitleLikeQueryServiceBO textTitleLikeQueryParamToServiceBO(SysBigTextTextTitleLikeQueryParam source);


	SysBigTextCreateServiceBO createRequestParamToServiceBO(SysBigTextCreateRequestParam source);


	SysBigTextUpdateServiceBO updateRequestParamToServiceBO(SysBigTextUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);

	BatchUpdateStateServiceBO batchUpdateStateParamToServiceBO(BatchUpdateStateRequestParam source);

	//=================================================================================

	SysBigText createServiceBOToEntity(SysBigTextCreateServiceBO source);

	List<SysBigText> createServiceBOListToEntityList(List<SysBigTextCreateServiceBO> source);

	SysBigText updateServiceBOToEntity(SysBigTextUpdateServiceBO source);

	List<SysBigText> updateServiceBOListToEntityList(List<SysBigTextUpdateServiceBO> source);

	//=================================================================================

	SysBigTextPageQueryMapperBO pageQueryServiceBOToMapperBO(SysBigTextPageQueryServiceBO source);

	SysBigTextTextTitleLikeQueryMapperBO textTitleLikeQueryServiceBOToMapperBO(SysBigTextTextTitleLikeQueryServiceBO source);


	BatchUpdateStateMapperBO batchUpdateStateServiceBOToMapperBO(BatchUpdateStateServiceBO source);

	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
