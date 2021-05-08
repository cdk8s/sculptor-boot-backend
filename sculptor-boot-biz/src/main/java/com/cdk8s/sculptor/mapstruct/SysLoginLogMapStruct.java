/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysLoginLogMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysloginlog.*;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysloginlog.*;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysloginlog.*;
import com.cdk8s.sculptor.pojo.dto.response.sysloginlog.SysLoginLogResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysLoginLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface SysLoginLogMapStruct {

	@Mappings({
			@Mapping(target = "boolLoginSuccessEnumString", expression = "java(com.cdk8s.sculptor.enums.BooleanEnum.getDescriptionByCode(source.getBoolLoginSuccessEnum()))"),
			@Mapping(target = "boolNowOnlineEnumString", expression = "java(com.cdk8s.sculptor.enums.BooleanEnum.getDescriptionByCode(source.getBoolNowOnlineEnum()))"),
			@Mapping(target = "offlineTypeEnumString", expression = "java(com.cdk8s.sculptor.enums.OfflineTypeEnum.getDescriptionByCode(source.getOfflineTypeEnum()))"),
			@Mapping(target = "boolNewUserEnumString", expression = "java(com.cdk8s.sculptor.enums.BooleanEnum.getDescriptionByCode(source.getBoolNewUserEnum()))"),
			@Mapping(target = "loginOriginEnumString", expression = "java(com.cdk8s.sculptor.enums.LoginOriginEnum.getDescriptionByCode(source.getLoginOriginEnum()))"),
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getCreateUserId()))"),
	})
	SysLoginLogResponseDTO toResponseDTO(SysLoginLog source);

	List<SysLoginLogResponseDTO> toResponseDTOList(List<SysLoginLog> source);

	//=================================================================================

	SysLoginLogPageQueryServiceBO pageQueryParamToServiceBO(SysLoginLogPageQueryParam source);

	SysLoginLogTokenServiceBO tokenRequestParamToServiceBO(SysLoginLogTokenRequestParam source);

	SysLoginLogTokenMapperBO tokenServiceBOToMapperBO(SysLoginLogTokenServiceBO source);

	SysLoginLogUsernameServiceBO usernameRequestParamToServiceBO(SysLoginLogUsernameRequestParam source);

	SysLoginLogUsernameMapperBO usernameServiceBOToMapperBO(SysLoginLogUsernameServiceBO source);


	SysLoginLogUsernameListServiceBO usernameListRequestParamToServiceBO(SysLoginLogUsernameListRequestParam source);

	SysLoginLogUsernameListMapperBO usernameListServiceBOToMapperBO(SysLoginLogUsernameListServiceBO source);


	SysLoginLogUserIdServiceBO userIdRequestParamToServiceBO(SysLoginLogUserIdRequestParam source);

	SysLoginLogUserIdMapperBO userIdServiceBOToMapperBO(SysLoginLogUserIdServiceBO source);

	SysLoginLogUserIdListServiceBO userIdListRequestParamToServiceBO(SysLoginLogUserIdListRequestParam source);

	SysLoginLogUserIdListMapperBO userIdListServiceBOToMapperBO(SysLoginLogUserIdListServiceBO source);

	SysLoginLogUserIdListToDeleteServiceBO userIdListToDeleteRequestParamToServiceBO(SysLoginLogUserIdListToDeleteRequestParam source);

	SysLoginLogUserIdListToDeleteMapperBO userIdListToDeleteServiceBOToMapperBO(SysLoginLogUserIdListToDeleteServiceBO source);


	SysLoginLogCreateServiceBO createRequestParamToServiceBO(SysLoginLogCreateRequestParam source);


	SysLoginLogUpdateServiceBO updateRequestParamToServiceBO(SysLoginLogUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);


	//=================================================================================

	SysLoginLog createServiceBOToEntity(SysLoginLogCreateServiceBO source);

	List<SysLoginLog> createServiceBOListToEntityList(List<SysLoginLogCreateServiceBO> source);

	SysLoginLog updateServiceBOToEntity(SysLoginLogUpdateServiceBO source);

	List<SysLoginLog> updateServiceBOListToEntityList(List<SysLoginLogUpdateServiceBO> source);

	//=================================================================================

	SysLoginLogPageQueryMapperBO pageQueryServiceBOToMapperBO(SysLoginLogPageQueryServiceBO source);


	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
