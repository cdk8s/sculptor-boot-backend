/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysEventLogMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.syseventlog.*;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syseventlog.*;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.syseventlog.*;
import com.cdk8s.sculptor.pojo.dto.response.syseventlog.SysEventLogResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysEventLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface SysEventLogMapStruct {

	@Mappings({
			@Mapping(target = "boolExecuteSuccessEnumString", expression = "java(com.cdk8s.sculptor.enums.BooleanEnum.getDescriptionByCode(source.getBoolExecuteSuccessEnum()))"),
			@Mapping(target = "operateTypeEnumString", expression = "java(com.cdk8s.sculptor.enums.OperateTypeEnum.getDescriptionByCode(source.getOperateTypeEnum()))"),
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getCreateUserId()))"),
	})
	SysEventLogResponseDTO toResponseDTO(SysEventLog source);

	List<SysEventLogResponseDTO> toResponseDTOList(List<SysEventLog> source);

	//=================================================================================

	SysEventLogPageQueryServiceBO pageQueryParamToServiceBO(SysEventLogPageQueryParam source);

	SysEventLogUsernameServiceBO usernameRequestParamToServiceBO(SysEventLogUsernameRequestParam source);

	SysEventLogUsernameMapperBO usernameServiceBOToMapperBO(SysEventLogUsernameServiceBO source);


	SysEventLogUsernameListServiceBO usernameListRequestParamToServiceBO(SysEventLogUsernameListRequestParam source);

	SysEventLogUsernameListMapperBO usernameListServiceBOToMapperBO(SysEventLogUsernameListServiceBO source);


	SysEventLogUserIdServiceBO userIdRequestParamToServiceBO(SysEventLogUserIdRequestParam source);

	SysEventLogUserIdMapperBO userIdServiceBOToMapperBO(SysEventLogUserIdServiceBO source);

	SysEventLogUserIdListServiceBO userIdListRequestParamToServiceBO(SysEventLogUserIdListRequestParam source);

	SysEventLogUserIdListMapperBO userIdListServiceBOToMapperBO(SysEventLogUserIdListServiceBO source);

	SysEventLogUserIdListToDeleteServiceBO userIdListToDeleteRequestParamToServiceBO(SysEventLogUserIdListToDeleteRequestParam source);

	SysEventLogUserIdListToDeleteMapperBO userIdListToDeleteServiceBOToMapperBO(SysEventLogUserIdListToDeleteServiceBO source);


	SysEventLogCreateServiceBO createRequestParamToServiceBO(SysEventLogCreateRequestParam source);


	SysEventLogUpdateServiceBO updateRequestParamToServiceBO(SysEventLogUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);


	//=================================================================================

	SysEventLog createServiceBOToEntity(SysEventLogCreateServiceBO source);

	List<SysEventLog> createServiceBOListToEntityList(List<SysEventLogCreateServiceBO> source);

	SysEventLog updateServiceBOToEntity(SysEventLogUpdateServiceBO source);

	List<SysEventLog> updateServiceBOListToEntityList(List<SysEventLogUpdateServiceBO> source);

	//=================================================================================

	SysEventLogPageQueryMapperBO pageQueryServiceBOToMapperBO(SysEventLogPageQueryServiceBO source);


	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
