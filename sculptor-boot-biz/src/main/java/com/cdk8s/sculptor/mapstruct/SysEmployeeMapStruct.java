/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysEmployeeMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysemployee.SysEmployeePageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysemployee.SysEmployeeUserIdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysemployee.SysEmployeeUserIdListToDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysemployee.SysEmployeeUserIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysemployee.*;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysemployee.*;
import com.cdk8s.sculptor.pojo.dto.response.sysemployee.SysEmployeeResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysEmployee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface SysEmployeeMapStruct {

	@Mappings({
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getCreateUserId()))"),
			@Mapping(target = "updateUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getUpdateUserId()))"),
	})
	SysEmployeeResponseDTO toResponseDTO(SysEmployee source);

	List<SysEmployeeResponseDTO> toResponseDTOList(List<SysEmployee> source);

	//=================================================================================

	SysEmployeePageQueryServiceBO pageQueryParamToServiceBO(SysEmployeePageQueryParam source);


	SysEmployeeUserIdServiceBO userIdRequestParamToServiceBO(SysEmployeeUserIdRequestParam source);

	SysEmployeeUserIdMapperBO userIdServiceBOToMapperBO(SysEmployeeUserIdServiceBO source);

	SysEmployeeUserIdListServiceBO userIdListRequestParamToServiceBO(SysEmployeeUserIdListRequestParam source);

	SysEmployeeUserIdListMapperBO userIdListServiceBOToMapperBO(SysEmployeeUserIdListServiceBO source);

	SysEmployeeUserIdListToDeleteServiceBO userIdListToDeleteRequestParamToServiceBO(SysEmployeeUserIdListToDeleteRequestParam source);

	SysEmployeeUserIdListToDeleteMapperBO userIdListToDeleteServiceBOToMapperBO(SysEmployeeUserIdListToDeleteServiceBO source);


	SysEmployeeCreateServiceBO createRequestParamToServiceBO(SysEmployeeCreateRequestParam source);


	SysEmployeeUpdateServiceBO updateRequestParamToServiceBO(SysEmployeeUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);


	//=================================================================================

	SysEmployee createServiceBOToEntity(SysEmployeeCreateServiceBO source);

	List<SysEmployee> createServiceBOListToEntityList(List<SysEmployeeCreateServiceBO> source);

	SysEmployee updateServiceBOToEntity(SysEmployeeUpdateServiceBO source);

	List<SysEmployee> updateServiceBOListToEntityList(List<SysEmployeeUpdateServiceBO> source);

	//=================================================================================

	SysEmployeePageQueryMapperBO pageQueryServiceBOToMapperBO(SysEmployeePageQueryServiceBO source);


	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
