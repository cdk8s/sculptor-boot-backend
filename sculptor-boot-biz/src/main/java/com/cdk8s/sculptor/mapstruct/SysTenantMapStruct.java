/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysTenantMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchUpdateStateMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.systenant.SysTenantPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.systenant.SysTenantTenantCodeListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.systenant.SysTenantTenantCodeMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.systenant.*;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.systenant.*;
import com.cdk8s.sculptor.pojo.dto.response.systenant.SysTenantResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysTenant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface SysTenantMapStruct {

	@Mappings({
			@Mapping(target = "stateEnumString", expression = "java(com.cdk8s.sculptor.enums.StateEnum.getDescriptionByCode(source.getStateEnum()))"),
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getCreateUserId()))"),
			@Mapping(target = "updateUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getUpdateUserId()))"),
	})
	SysTenantResponseDTO toResponseDTO(SysTenant source);

	List<SysTenantResponseDTO> toResponseDTOList(List<SysTenant> source);

	//=================================================================================

	SysTenantPageQueryServiceBO pageQueryParamToServiceBO(SysTenantPageQueryParam source);

	SysTenantTenantCodeServiceBO tenantCodeRequestParamToServiceBO(SysTenantTenantCodeRequestParam source);

	SysTenantTenantCodeMapperBO tenantCodeServiceBOToMapperBO(SysTenantTenantCodeServiceBO source);


	SysTenantTenantCodeListServiceBO tenantCodeListRequestParamToServiceBO(SysTenantTenantCodeListRequestParam source);

	SysTenantTenantCodeListMapperBO tenantCodeListServiceBOToMapperBO(SysTenantTenantCodeListServiceBO source);


	SysTenantCreateServiceBO createRequestParamToServiceBO(SysTenantCreateRequestParam source);


	SysTenantUpdateServiceBO updateRequestParamToServiceBO(SysTenantUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);

	BatchUpdateStateServiceBO batchUpdateStateParamToServiceBO(BatchUpdateStateRequestParam source);

	//=================================================================================

	SysTenant createServiceBOToEntity(SysTenantCreateServiceBO source);

	List<SysTenant> createServiceBOListToEntityList(List<SysTenantCreateServiceBO> source);

	SysTenant updateServiceBOToEntity(SysTenantUpdateServiceBO source);

	List<SysTenant> updateServiceBOListToEntityList(List<SysTenantUpdateServiceBO> source);

	//=================================================================================

	SysTenantPageQueryMapperBO pageQueryServiceBOToMapperBO(SysTenantPageQueryServiceBO source);


	BatchUpdateStateMapperBO batchUpdateStateServiceBOToMapperBO(BatchUpdateStateServiceBO source);

	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
