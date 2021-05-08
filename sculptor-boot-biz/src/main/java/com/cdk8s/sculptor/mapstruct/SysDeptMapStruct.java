/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysDeptMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchUpdateStateMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysdept.SysDeptDeptCodeListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysdept.SysDeptDeptCodeMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysdept.SysDeptPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysdept.*;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysdept.*;
import com.cdk8s.sculptor.pojo.dto.response.sysdept.SysDeptResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysDept;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface SysDeptMapStruct {

	@Mappings({
			@Mapping(target = "stateEnumString", expression = "java(com.cdk8s.sculptor.enums.StateEnum.getDescriptionByCode(source.getStateEnum()))"),
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getCreateUserId()))"),
			@Mapping(target = "updateUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getUpdateUserId()))"),
	})
	SysDeptResponseDTO toResponseDTO(SysDept source);

	List<SysDeptResponseDTO> toResponseDTOList(List<SysDept> source);

	//=================================================================================

	SysDeptPageQueryServiceBO pageQueryParamToServiceBO(SysDeptPageQueryParam source);

	SysDeptDeptCodeServiceBO deptCodeRequestParamToServiceBO(SysDeptDeptCodeRequestParam source);

	SysDeptDeptCodeMapperBO deptCodeServiceBOToMapperBO(SysDeptDeptCodeServiceBO source);


	SysDeptDeptCodeListServiceBO deptCodeListRequestParamToServiceBO(SysDeptDeptCodeListRequestParam source);

	SysDeptDeptCodeListMapperBO deptCodeListServiceBOToMapperBO(SysDeptDeptCodeListServiceBO source);


	SysDeptCreateServiceBO createRequestParamToServiceBO(SysDeptCreateRequestParam source);


	SysDeptUpdateServiceBO updateRequestParamToServiceBO(SysDeptUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);

	BatchUpdateStateServiceBO batchUpdateStateParamToServiceBO(BatchUpdateStateRequestParam source);

	//=================================================================================

	SysDept createServiceBOToEntity(SysDeptCreateServiceBO source);

	List<SysDept> createServiceBOListToEntityList(List<SysDeptCreateServiceBO> source);

	SysDept updateServiceBOToEntity(SysDeptUpdateServiceBO source);

	List<SysDept> updateServiceBOListToEntityList(List<SysDeptUpdateServiceBO> source);

	//=================================================================================

	SysDeptPageQueryMapperBO pageQueryServiceBOToMapperBO(SysDeptPageQueryServiceBO source);


	BatchUpdateStateMapperBO batchUpdateStateServiceBOToMapperBO(BatchUpdateStateServiceBO source);

	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
