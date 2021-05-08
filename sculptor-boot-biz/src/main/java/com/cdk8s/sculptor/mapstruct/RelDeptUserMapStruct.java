/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：RelDeptUserMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.reldeptuser.*;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.reldeptuser.*;
import com.cdk8s.sculptor.pojo.bo.service.reldeptuser.bases.RelDeptUserBatchCreateServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.reldeptuser.*;
import com.cdk8s.sculptor.pojo.dto.response.reldeptuser.RelDeptUserResponseDTO;
import com.cdk8s.sculptor.pojo.entity.RelDeptUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface RelDeptUserMapStruct {

	@Mappings({
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getCreateUserId()))"),
	})
	RelDeptUserResponseDTO toResponseDTO(RelDeptUser source);

	List<RelDeptUserResponseDTO> toResponseDTOList(List<RelDeptUser> source);

	//=================================================================================

	RelDeptUserPageQueryServiceBO pageQueryParamToServiceBO(RelDeptUserPageQueryParam source);


	RelDeptUserDeptIdServiceBO deptIdRequestParamToServiceBO(RelDeptUserDeptIdRequestParam source);

	RelDeptUserDeptIdMapperBO deptIdServiceBOToMapperBO(RelDeptUserDeptIdServiceBO source);

	RelDeptUserDeptIdListServiceBO deptIdListRequestParamToServiceBO(RelDeptUserDeptIdListRequestParam source);

	RelDeptUserDeptIdListMapperBO deptIdListServiceBOToMapperBO(RelDeptUserDeptIdListServiceBO source);

	RelDeptUserDeptIdListToDeleteServiceBO deptIdListToDeleteRequestParamToServiceBO(RelDeptUserDeptIdListToDeleteRequestParam source);

	RelDeptUserDeptIdListToDeleteMapperBO deptIdListToDeleteServiceBOToMapperBO(RelDeptUserDeptIdListToDeleteServiceBO source);

	RelDeptUserUserIdServiceBO userIdRequestParamToServiceBO(RelDeptUserUserIdRequestParam source);

	RelDeptUserUserIdMapperBO userIdServiceBOToMapperBO(RelDeptUserUserIdServiceBO source);

	RelDeptUserUserIdListServiceBO userIdListRequestParamToServiceBO(RelDeptUserUserIdListRequestParam source);

	RelDeptUserUserIdListMapperBO userIdListServiceBOToMapperBO(RelDeptUserUserIdListServiceBO source);

	RelDeptUserUserIdListToDeleteServiceBO userIdListToDeleteRequestParamToServiceBO(RelDeptUserUserIdListToDeleteRequestParam source);

	RelDeptUserUserIdListToDeleteMapperBO userIdListToDeleteServiceBOToMapperBO(RelDeptUserUserIdListToDeleteServiceBO source);


	RelDeptUserCreateServiceBO createRequestParamToServiceBO(RelDeptUserCreateRequestParam source);

	RelDeptUserBatchCreateServiceBO batchCreateRequestParamToServiceBO(RelDeptUserBatchCreateRequestParam source);

	RelDeptUserUpdateServiceBO updateRequestParamToServiceBO(RelDeptUserUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);

	RelDeptUserDeptIdAndUserIdToDeleteServiceBO deleteByDeptIdAndUserIdParamToServiceBO(RelDeptUserDeptIdAndUserIdToDeleteRequestParam source);


	//=================================================================================

	RelDeptUser createServiceBOToEntity(RelDeptUserCreateServiceBO source);

	List<RelDeptUser> createServiceBOListToEntityList(List<RelDeptUserCreateServiceBO> source);

	RelDeptUser updateServiceBOToEntity(RelDeptUserUpdateServiceBO source);

	List<RelDeptUser> updateServiceBOListToEntityList(List<RelDeptUserUpdateServiceBO> source);

	//=================================================================================

	RelDeptUserPageQueryMapperBO pageQueryServiceBOToMapperBO(RelDeptUserPageQueryServiceBO source);


	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	RelDeptUserDeptIdAndUserIdToDeleteMapperBO deleteByDeptIdAndUserIdServiceBOToMapperBO(RelDeptUserDeptIdAndUserIdToDeleteServiceBO source);
	//=================================================================================

}
