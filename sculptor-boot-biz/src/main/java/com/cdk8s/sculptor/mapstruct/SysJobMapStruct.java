/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysJobMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchUpdateStateMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysjob.SysJobJobNameListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysjob.SysJobJobNameMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysjob.SysJobPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysjob.*;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysjob.*;
import com.cdk8s.sculptor.pojo.dto.response.sysjob.SysJobResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysJob;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface SysJobMapStruct {

	@Mappings({
			@Mapping(target = "misfirePolicyEnumString", expression = "java(com.cdk8s.sculptor.enums.MisfirePolicyEnum.getDescriptionByCode(source.getMisfirePolicyEnum()))"),
			@Mapping(target = "boolSupportConcurrentEnumString", expression = "java(com.cdk8s.sculptor.enums.BooleanEnum.getDescriptionByCode(source.getBoolSupportConcurrentEnum()))"),
			@Mapping(target = "stateEnumString", expression = "java(com.cdk8s.sculptor.enums.StateEnum.getDescriptionByCode(source.getStateEnum()))"),
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getCreateUserId()))"),
			@Mapping(target = "updateUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getUpdateUserId()))"),
	})
	SysJobResponseDTO toResponseDTO(SysJob source);

	List<SysJobResponseDTO> toResponseDTOList(List<SysJob> source);

	//=================================================================================

	SysJobPageQueryServiceBO pageQueryParamToServiceBO(SysJobPageQueryParam source);

	SysJobJobNameServiceBO jobNameRequestParamToServiceBO(SysJobJobNameRequestParam source);

	SysJobJobNameMapperBO jobNameServiceBOToMapperBO(SysJobJobNameServiceBO source);


	SysJobJobNameListServiceBO jobNameListRequestParamToServiceBO(SysJobJobNameListRequestParam source);

	SysJobJobNameListMapperBO jobNameListServiceBOToMapperBO(SysJobJobNameListServiceBO source);


	SysJobCreateServiceBO createRequestParamToServiceBO(SysJobCreateRequestParam source);


	SysJobUpdateServiceBO updateRequestParamToServiceBO(SysJobUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);

	BatchUpdateStateServiceBO batchUpdateStateParamToServiceBO(BatchUpdateStateRequestParam source);

	//=================================================================================

	SysJob createServiceBOToEntity(SysJobCreateServiceBO source);

	List<SysJob> createServiceBOListToEntityList(List<SysJobCreateServiceBO> source);

	SysJob updateServiceBOToEntity(SysJobUpdateServiceBO source);

	List<SysJob> updateServiceBOListToEntityList(List<SysJobUpdateServiceBO> source);

	//=================================================================================

	SysJobPageQueryMapperBO pageQueryServiceBOToMapperBO(SysJobPageQueryServiceBO source);


	BatchUpdateStateMapperBO batchUpdateStateServiceBOToMapperBO(BatchUpdateStateServiceBO source);

	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
