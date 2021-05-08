/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysUserInfoMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysuserinfo.*;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysuserinfo.*;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysuserinfo.*;
import com.cdk8s.sculptor.pojo.dto.response.sysuserinfo.SysUserInfoResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysUserInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface SysUserInfoMapStruct {

	@Mappings({
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getCreateUserId()))"),
			@Mapping(target = "updateUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getUpdateUserId()))"),
	})
	SysUserInfoResponseDTO toResponseDTO(SysUserInfo source);

	List<SysUserInfoResponseDTO> toResponseDTOList(List<SysUserInfo> source);

	//=================================================================================

	SysUserInfoPageQueryServiceBO pageQueryParamToServiceBO(SysUserInfoPageQueryParam source);

	SysUserInfoWeixinUnionidServiceBO weixinUnionidRequestParamToServiceBO(SysUserInfoWeixinUnionidRequestParam source);

	SysUserInfoWeixinUnionidMapperBO weixinUnionidServiceBOToMapperBO(SysUserInfoWeixinUnionidServiceBO source);

	SysUserInfoWeixinOpenidServiceBO weixinOpenidRequestParamToServiceBO(SysUserInfoWeixinOpenidRequestParam source);

	SysUserInfoWeixinOpenidMapperBO weixinOpenidServiceBOToMapperBO(SysUserInfoWeixinOpenidServiceBO source);

	SysUserInfoIdCardServiceBO idCardRequestParamToServiceBO(SysUserInfoIdCardRequestParam source);

	SysUserInfoIdCardMapperBO idCardServiceBOToMapperBO(SysUserInfoIdCardServiceBO source);


	SysUserInfoWeixinUnionidListServiceBO weixinUnionidListRequestParamToServiceBO(SysUserInfoWeixinUnionidListRequestParam source);

	SysUserInfoWeixinUnionidListMapperBO weixinUnionidListServiceBOToMapperBO(SysUserInfoWeixinUnionidListServiceBO source);

	SysUserInfoWeixinOpenidListServiceBO weixinOpenidListRequestParamToServiceBO(SysUserInfoWeixinOpenidListRequestParam source);

	SysUserInfoWeixinOpenidListMapperBO weixinOpenidListServiceBOToMapperBO(SysUserInfoWeixinOpenidListServiceBO source);

	SysUserInfoIdCardListServiceBO idCardListRequestParamToServiceBO(SysUserInfoIdCardListRequestParam source);

	SysUserInfoIdCardListMapperBO idCardListServiceBOToMapperBO(SysUserInfoIdCardListServiceBO source);


	SysUserInfoUserIdServiceBO userIdRequestParamToServiceBO(SysUserInfoUserIdRequestParam source);

	SysUserInfoUserIdMapperBO userIdServiceBOToMapperBO(SysUserInfoUserIdServiceBO source);

	SysUserInfoUserIdListServiceBO userIdListRequestParamToServiceBO(SysUserInfoUserIdListRequestParam source);

	SysUserInfoUserIdListMapperBO userIdListServiceBOToMapperBO(SysUserInfoUserIdListServiceBO source);

	SysUserInfoUserIdListToDeleteServiceBO userIdListToDeleteRequestParamToServiceBO(SysUserInfoUserIdListToDeleteRequestParam source);

	SysUserInfoUserIdListToDeleteMapperBO userIdListToDeleteServiceBOToMapperBO(SysUserInfoUserIdListToDeleteServiceBO source);


	SysUserInfoCreateServiceBO createRequestParamToServiceBO(SysUserInfoCreateRequestParam source);


	SysUserInfoUpdateServiceBO updateRequestParamToServiceBO(SysUserInfoUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);


	//=================================================================================

	@Mappings({
			@Mapping(source = "updateDate", target = "createDate"),
			@Mapping(source = "updateUserId", target = "createUserId"),
	})
	SysUserInfoCreateServiceBO updateServiceBOToCreateServiceBO(SysUserInfoUpdateServiceBO source);

	//=================================================================================

	SysUserInfo createServiceBOToEntity(SysUserInfoCreateServiceBO source);

	List<SysUserInfo> createServiceBOListToEntityList(List<SysUserInfoCreateServiceBO> source);

	SysUserInfo updateServiceBOToEntity(SysUserInfoUpdateServiceBO source);

	List<SysUserInfo> updateServiceBOListToEntityList(List<SysUserInfoUpdateServiceBO> source);

	//=================================================================================

	SysUserInfoPageQueryMapperBO pageQueryServiceBOToMapperBO(SysUserInfoPageQueryServiceBO source);


	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
