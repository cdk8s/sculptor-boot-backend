/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysRoleMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchUpdateStateMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysrole.SysRolePageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysrole.SysRoleRoleCodeListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysrole.SysRoleRoleCodeMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysrole.*;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysrole.*;
import com.cdk8s.sculptor.pojo.dto.response.sysrole.SysRoleResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface SysRoleMapStruct {

	@Mappings({
			@Mapping(target = "boolTemplateEnumString", expression = "java(com.cdk8s.sculptor.enums.BooleanEnum.getDescriptionByCode(source.getBoolTemplateEnum()))"),
			@Mapping(target = "stateEnumString", expression = "java(com.cdk8s.sculptor.enums.StateEnum.getDescriptionByCode(source.getStateEnum()))"),
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getCreateUserId()))"),
			@Mapping(target = "updateUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getUpdateUserId()))"),
	})
	SysRoleResponseDTO toResponseDTO(SysRole source);

	List<SysRoleResponseDTO> toResponseDTOList(List<SysRole> source);

	//=================================================================================

	SysRolePageQueryServiceBO pageQueryParamToServiceBO(SysRolePageQueryParam source);

	SysRoleRoleCodeServiceBO roleCodeRequestParamToServiceBO(SysRoleRoleCodeRequestParam source);

	SysRoleRoleCodeMapperBO roleCodeServiceBOToMapperBO(SysRoleRoleCodeServiceBO source);


	SysRoleRoleCodeListServiceBO roleCodeListRequestParamToServiceBO(SysRoleRoleCodeListRequestParam source);

	SysRoleRoleCodeListMapperBO roleCodeListServiceBOToMapperBO(SysRoleRoleCodeListServiceBO source);


	SysRoleCreateServiceBO createRequestParamToServiceBO(SysRoleCreateRequestParam source);


	SysRoleUpdateServiceBO updateRequestParamToServiceBO(SysRoleUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);

	BatchUpdateStateServiceBO batchUpdateStateParamToServiceBO(BatchUpdateStateRequestParam source);

	//=================================================================================

	SysRole createServiceBOToEntity(SysRoleCreateServiceBO source);

	List<SysRole> createServiceBOListToEntityList(List<SysRoleCreateServiceBO> source);

	SysRole updateServiceBOToEntity(SysRoleUpdateServiceBO source);

	List<SysRole> updateServiceBOListToEntityList(List<SysRoleUpdateServiceBO> source);

	//=================================================================================

	SysRolePageQueryMapperBO pageQueryServiceBOToMapperBO(SysRolePageQueryServiceBO source);


	BatchUpdateStateMapperBO batchUpdateStateServiceBOToMapperBO(BatchUpdateStateServiceBO source);

	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
