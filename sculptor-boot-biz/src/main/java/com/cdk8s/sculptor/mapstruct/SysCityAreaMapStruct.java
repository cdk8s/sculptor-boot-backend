/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysCityAreaMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.syscityarea.SysCityAreaPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syscityarea.SysCityAreaCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syscityarea.SysCityAreaPageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syscityarea.SysCityAreaUpdateServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.syscityarea.SysCityAreaCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.syscityarea.SysCityAreaPageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.syscityarea.SysCityAreaUpdateRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.syscityarea.SysCityAreaResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysCityArea;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface SysCityAreaMapStruct {

	@Mappings({
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getCreateUserId()))"),
	})
	SysCityAreaResponseDTO toResponseDTO(SysCityArea source);

	List<SysCityAreaResponseDTO> toResponseDTOList(List<SysCityArea> source);

	//=================================================================================

	SysCityAreaPageQueryServiceBO pageQueryParamToServiceBO(SysCityAreaPageQueryParam source);


	SysCityAreaCreateServiceBO createRequestParamToServiceBO(SysCityAreaCreateRequestParam source);


	SysCityAreaUpdateServiceBO updateRequestParamToServiceBO(SysCityAreaUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);


	//=================================================================================

	SysCityArea createServiceBOToEntity(SysCityAreaCreateServiceBO source);

	List<SysCityArea> createServiceBOListToEntityList(List<SysCityAreaCreateServiceBO> source);

	SysCityArea updateServiceBOToEntity(SysCityAreaUpdateServiceBO source);

	List<SysCityArea> updateServiceBOListToEntityList(List<SysCityAreaUpdateServiceBO> source);

	//=================================================================================

	SysCityAreaPageQueryMapperBO pageQueryServiceBOToMapperBO(SysCityAreaPageQueryServiceBO source);


	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
