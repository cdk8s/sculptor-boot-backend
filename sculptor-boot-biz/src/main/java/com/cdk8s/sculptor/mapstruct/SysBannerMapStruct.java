/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysBannerMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysbanner.*;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysbanner.*;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysbanner.*;
import com.cdk8s.sculptor.pojo.dto.response.sysbanner.SysBannerResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysBanner;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface SysBannerMapStruct {

	@Mappings({
			@Mapping(target = "bannerJumpTypeEnumString", expression = "java(com.cdk8s.sculptor.enums.BannerJumpTypeEnum.getDescriptionByCode(source.getBannerJumpTypeEnum()))"),
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getCreateUserId()))"),
			@Mapping(target = "updateUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getUpdateUserId()))"),
	})
	SysBannerResponseDTO toResponseDTO(SysBanner source);

	List<SysBannerResponseDTO> toResponseDTOList(List<SysBanner> source);

	//=================================================================================

	SysBannerPageQueryServiceBO pageQueryParamToServiceBO(SysBannerPageQueryParam source);

	SysBannerJumpTypeCodeServiceBO jumpTypeCodeRequestParamToServiceBO(SysBannerJumpTypeCodeRequestParam source);

	SysBannerJumpTypeCodeMapperBO jumpTypeCodeServiceBOToMapperBO(SysBannerJumpTypeCodeServiceBO source);

	SysBannerBannerCodeServiceBO bannerCodeRequestParamToServiceBO(SysBannerBannerCodeRequestParam source);

	SysBannerBannerCodeMapperBO bannerCodeServiceBOToMapperBO(SysBannerBannerCodeServiceBO source);


	SysBannerBannerCodeListServiceBO bannerCodeListRequestParamToServiceBO(SysBannerBannerCodeListRequestParam source);

	SysBannerBannerCodeListMapperBO bannerCodeListServiceBOToMapperBO(SysBannerBannerCodeListServiceBO source);


	SysBannerJumpObjectIdServiceBO jumpObjectIdRequestParamToServiceBO(SysBannerJumpObjectIdRequestParam source);

	SysBannerJumpObjectIdMapperBO jumpObjectIdServiceBOToMapperBO(SysBannerJumpObjectIdServiceBO source);

	SysBannerJumpObjectIdListServiceBO jumpObjectIdListRequestParamToServiceBO(SysBannerJumpObjectIdListRequestParam source);

	SysBannerJumpObjectIdListMapperBO jumpObjectIdListServiceBOToMapperBO(SysBannerJumpObjectIdListServiceBO source);

	SysBannerJumpObjectIdListToDeleteServiceBO jumpObjectIdListToDeleteRequestParamToServiceBO(SysBannerJumpObjectIdListToDeleteRequestParam source);

	SysBannerJumpObjectIdListToDeleteMapperBO jumpObjectIdListToDeleteServiceBOToMapperBO(SysBannerJumpObjectIdListToDeleteServiceBO source);


	SysBannerBannerTitleLikeQueryServiceBO bannerTitleLikeQueryParamToServiceBO(SysBannerBannerTitleLikeQueryParam source);


	SysBannerCreateServiceBO createRequestParamToServiceBO(SysBannerCreateRequestParam source);


	SysBannerUpdateServiceBO updateRequestParamToServiceBO(SysBannerUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);


	//=================================================================================

	SysBanner createServiceBOToEntity(SysBannerCreateServiceBO source);

	List<SysBanner> createServiceBOListToEntityList(List<SysBannerCreateServiceBO> source);

	SysBanner updateServiceBOToEntity(SysBannerUpdateServiceBO source);

	List<SysBanner> updateServiceBOListToEntityList(List<SysBannerUpdateServiceBO> source);

	//=================================================================================

	SysBannerPageQueryMapperBO pageQueryServiceBOToMapperBO(SysBannerPageQueryServiceBO source);

	SysBannerBannerTitleLikeQueryMapperBO bannerTitleLikeQueryServiceBOToMapperBO(SysBannerBannerTitleLikeQueryServiceBO source);


	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
