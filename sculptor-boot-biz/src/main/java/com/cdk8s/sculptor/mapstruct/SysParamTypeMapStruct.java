/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysParamTypeMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchUpdateStateMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysparamtype.SysParamTypePageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysparamtype.SysParamTypeTypeCodeListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysparamtype.SysParamTypeTypeCodeMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysparamtype.*;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysparamtype.*;
import com.cdk8s.sculptor.pojo.dto.response.sysparamtype.SysParamTypeResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysParamType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface SysParamTypeMapStruct {

	@Mappings({
			@Mapping(target = "stateEnumString", expression = "java(com.cdk8s.sculptor.enums.StateEnum.getDescriptionByCode(source.getStateEnum()))"),
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getCreateUserId()))"),
			@Mapping(target = "updateUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getUpdateUserId()))"),
	})
	SysParamTypeResponseDTO toResponseDTO(SysParamType source);

	List<SysParamTypeResponseDTO> toResponseDTOList(List<SysParamType> source);

	//=================================================================================

	SysParamTypePageQueryServiceBO pageQueryParamToServiceBO(SysParamTypePageQueryParam source);

	SysParamTypeTypeCodeServiceBO typeCodeRequestParamToServiceBO(SysParamTypeTypeCodeRequestParam source);

	SysParamTypeTypeCodeMapperBO typeCodeServiceBOToMapperBO(SysParamTypeTypeCodeServiceBO source);


	SysParamTypeTypeCodeListServiceBO typeCodeListRequestParamToServiceBO(SysParamTypeTypeCodeListRequestParam source);

	SysParamTypeTypeCodeListMapperBO typeCodeListServiceBOToMapperBO(SysParamTypeTypeCodeListServiceBO source);


	SysParamTypeCreateServiceBO createRequestParamToServiceBO(SysParamTypeCreateRequestParam source);


	SysParamTypeUpdateServiceBO updateRequestParamToServiceBO(SysParamTypeUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);

	BatchUpdateStateServiceBO batchUpdateStateParamToServiceBO(BatchUpdateStateRequestParam source);

	//=================================================================================

	SysParamType createServiceBOToEntity(SysParamTypeCreateServiceBO source);

	List<SysParamType> createServiceBOListToEntityList(List<SysParamTypeCreateServiceBO> source);

	SysParamType updateServiceBOToEntity(SysParamTypeUpdateServiceBO source);

	List<SysParamType> updateServiceBOListToEntityList(List<SysParamTypeUpdateServiceBO> source);

	//=================================================================================

	SysParamTypePageQueryMapperBO pageQueryServiceBOToMapperBO(SysParamTypePageQueryServiceBO source);


	BatchUpdateStateMapperBO batchUpdateStateServiceBOToMapperBO(BatchUpdateStateServiceBO source);

	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
