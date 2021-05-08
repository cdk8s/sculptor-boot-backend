/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysJobLogMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysjoblog.*;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysjoblog.*;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysjoblog.*;
import com.cdk8s.sculptor.pojo.dto.response.sysjoblog.SysJobLogResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysJobLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface SysJobLogMapStruct {

	@Mappings({
			@Mapping(target = "boolExecuteSuccessEnumString", expression = "java(com.cdk8s.sculptor.enums.BooleanEnum.getDescriptionByCode(source.getBoolExecuteSuccessEnum()))"),
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getCreateUserId()))"),
	})
	SysJobLogResponseDTO toResponseDTO(SysJobLog source);

	List<SysJobLogResponseDTO> toResponseDTOList(List<SysJobLog> source);

	//=================================================================================

	SysJobLogPageQueryServiceBO pageQueryParamToServiceBO(SysJobLogPageQueryParam source);

	SysJobLogJobNameServiceBO jobNameRequestParamToServiceBO(SysJobLogJobNameRequestParam source);

	SysJobLogJobNameMapperBO jobNameServiceBOToMapperBO(SysJobLogJobNameServiceBO source);


	SysJobLogJobNameListServiceBO jobNameListRequestParamToServiceBO(SysJobLogJobNameListRequestParam source);

	SysJobLogJobNameListMapperBO jobNameListServiceBOToMapperBO(SysJobLogJobNameListServiceBO source);


	SysJobLogJobIdServiceBO jobIdRequestParamToServiceBO(SysJobLogJobIdRequestParam source);

	SysJobLogJobIdMapperBO jobIdServiceBOToMapperBO(SysJobLogJobIdServiceBO source);

	SysJobLogJobIdListServiceBO jobIdListRequestParamToServiceBO(SysJobLogJobIdListRequestParam source);

	SysJobLogJobIdListMapperBO jobIdListServiceBOToMapperBO(SysJobLogJobIdListServiceBO source);

	SysJobLogJobIdListToDeleteServiceBO jobIdListToDeleteRequestParamToServiceBO(SysJobLogJobIdListToDeleteRequestParam source);

	SysJobLogJobIdListToDeleteMapperBO jobIdListToDeleteServiceBOToMapperBO(SysJobLogJobIdListToDeleteServiceBO source);


	SysJobLogCreateServiceBO createRequestParamToServiceBO(SysJobLogCreateRequestParam source);


	SysJobLogUpdateServiceBO updateRequestParamToServiceBO(SysJobLogUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);


	//=================================================================================

	SysJobLog createServiceBOToEntity(SysJobLogCreateServiceBO source);

	List<SysJobLog> createServiceBOListToEntityList(List<SysJobLogCreateServiceBO> source);

	SysJobLog updateServiceBOToEntity(SysJobLogUpdateServiceBO source);

	List<SysJobLog> updateServiceBOListToEntityList(List<SysJobLogUpdateServiceBO> source);

	//=================================================================================

	SysJobLogPageQueryMapperBO pageQueryServiceBOToMapperBO(SysJobLogPageQueryServiceBO source);


	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
