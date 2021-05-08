/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysSmsLoginLogMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.syssmsloginlog.*;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syssmsloginlog.*;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.syssmsloginlog.*;
import com.cdk8s.sculptor.pojo.dto.response.syssmsloginlog.SysSmsLoginLogResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysSmsLoginLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface SysSmsLoginLogMapStruct {

	@Mappings({
			@Mapping(target = "smsProviderTypeEnumString", expression = "java(com.cdk8s.sculptor.enums.SmsProviderTypeEnum.getDescriptionByCode(source.getSmsProviderTypeEnum()))"),
			@Mapping(target = "boolServiceStateEnumString", expression = "java(com.cdk8s.sculptor.enums.BooleanEnum.getDescriptionByCode(source.getBoolServiceStateEnum()))"),
			@Mapping(target = "boolUseEnumString", expression = "java(com.cdk8s.sculptor.enums.BooleanEnum.getDescriptionByCode(source.getBoolUseEnum()))"),
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getCreateUserId()))"),
	})
	SysSmsLoginLogResponseDTO toResponseDTO(SysSmsLoginLog source);

	List<SysSmsLoginLogResponseDTO> toResponseDTOList(List<SysSmsLoginLog> source);

	//=================================================================================

	SysSmsLoginLogPageQueryServiceBO pageQueryParamToServiceBO(SysSmsLoginLogPageQueryParam source);


	SysSmsLoginLogUserIdServiceBO userIdRequestParamToServiceBO(SysSmsLoginLogUserIdRequestParam source);

	SysSmsLoginLogUserIdMapperBO userIdServiceBOToMapperBO(SysSmsLoginLogUserIdServiceBO source);

	SysSmsLoginLogUserIdListServiceBO userIdListRequestParamToServiceBO(SysSmsLoginLogUserIdListRequestParam source);

	SysSmsLoginLogUserIdListMapperBO userIdListServiceBOToMapperBO(SysSmsLoginLogUserIdListServiceBO source);

	SysSmsLoginLogUserIdListToDeleteServiceBO userIdListToDeleteRequestParamToServiceBO(SysSmsLoginLogUserIdListToDeleteRequestParam source);

	SysSmsLoginLogUserIdListToDeleteMapperBO userIdListToDeleteServiceBOToMapperBO(SysSmsLoginLogUserIdListToDeleteServiceBO source);

	SysSmsLoginLogUserMobilePhoneServiceBO userMobilePhoneRequestParamToServiceBO(SysSmsLoginLogUserMobilePhoneRequestParam source);

	SysSmsLoginLogUserMobilePhoneMapperBO userMobilePhoneServiceBOToMapperBO(SysSmsLoginLogUserMobilePhoneServiceBO source);

	SysSmsLoginLogUserMobilePhoneListServiceBO userMobilePhoneListRequestParamToServiceBO(SysSmsLoginLogUserMobilePhoneListRequestParam source);

	SysSmsLoginLogUserMobilePhoneListMapperBO userMobilePhoneListServiceBOToMapperBO(SysSmsLoginLogUserMobilePhoneListServiceBO source);

	SysSmsLoginLogUserMobilePhoneListToDeleteServiceBO userMobilePhoneListToDeleteRequestParamToServiceBO(SysSmsLoginLogUserMobilePhoneListToDeleteRequestParam source);

	SysSmsLoginLogUserMobilePhoneListToDeleteMapperBO userMobilePhoneListToDeleteServiceBOToMapperBO(SysSmsLoginLogUserMobilePhoneListToDeleteServiceBO source);


	SysSmsLoginLogCreateServiceBO createRequestParamToServiceBO(SysSmsLoginLogCreateRequestParam source);


	SysSmsLoginLogUpdateServiceBO updateRequestParamToServiceBO(SysSmsLoginLogUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);


	//=================================================================================

	SysSmsLoginLog createServiceBOToEntity(SysSmsLoginLogCreateServiceBO source);

	List<SysSmsLoginLog> createServiceBOListToEntityList(List<SysSmsLoginLogCreateServiceBO> source);

	SysSmsLoginLog updateServiceBOToEntity(SysSmsLoginLogUpdateServiceBO source);

	List<SysSmsLoginLog> updateServiceBOListToEntityList(List<SysSmsLoginLogUpdateServiceBO> source);

	//=================================================================================

	SysSmsLoginLogPageQueryMapperBO pageQueryServiceBOToMapperBO(SysSmsLoginLogPageQueryServiceBO source);


	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
