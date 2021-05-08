/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysUserMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchUpdateStateMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysuser.*;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateStateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysuser.*;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysuser.*;
import com.cdk8s.sculptor.pojo.dto.response.sysuser.SysUserResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface SysUserMapStruct {

	@Mappings({
			@Mapping(target = "genderEnumString", expression = "java(com.cdk8s.sculptor.enums.GenderEnum.getDescriptionByCode(source.getGenderEnum()))"),
			@Mapping(target = "userTypeEnumString", expression = "java(com.cdk8s.sculptor.enums.UserTypeEnum.getDescriptionByCode(source.getUserTypeEnum()))"),
			@Mapping(target = "registerTypeEnumString", expression = "java(com.cdk8s.sculptor.enums.RegisterTypeEnum.getDescriptionByCode(source.getRegisterTypeEnum()))"),
			@Mapping(target = "registerOriginEnumString", expression = "java(com.cdk8s.sculptor.enums.RegisterOriginEnum.getDescriptionByCode(source.getRegisterOriginEnum()))"),
			@Mapping(target = "stateEnumString", expression = "java(com.cdk8s.sculptor.enums.StateEnum.getDescriptionByCode(source.getStateEnum()))"),
			@Mapping(target = "createUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getCreateUserId()))"),
			@Mapping(target = "updateUsername", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getRealNameByUserId(source.getUpdateUserId()))"),
	})
	SysUserResponseDTO toResponseDTO(SysUser source);

	List<SysUserResponseDTO> toResponseDTOList(List<SysUser> source);

	//=================================================================================

	SysUserPageQueryServiceBO pageQueryParamToServiceBO(SysUserPageQueryParam source);

	SysUserUsernameServiceBO usernameRequestParamToServiceBO(SysUserUsernameRequestParam source);

	SysUserUsernameMapperBO usernameServiceBOToMapperBO(SysUserUsernameServiceBO source);

	SysUserUserEmailServiceBO userEmailRequestParamToServiceBO(SysUserUserEmailRequestParam source);

	SysUserUserEmailMapperBO userEmailServiceBOToMapperBO(SysUserUserEmailServiceBO source);

	SysUserNicknameServiceBO nicknameRequestParamToServiceBO(SysUserNicknameRequestParam source);

	SysUserNicknameMapperBO nicknameServiceBOToMapperBO(SysUserNicknameServiceBO source);

	SysUserMobilePhoneServiceBO mobilePhoneRequestParamToServiceBO(SysUserMobilePhoneRequestParam source);

	SysUserMobilePhoneMapperBO mobilePhoneServiceBOToMapperBO(SysUserMobilePhoneServiceBO source);


	SysUserRealNameLikeQueryServiceBO realNameLikeQueryParamToServiceBO(SysUserRealNameLikeQueryParam source);


	SysUserCreateServiceBO createRequestParamToServiceBO(SysUserCreateRequestParam source);


	SysUserUpdateServiceBO updateRequestParamToServiceBO(SysUserUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);

	BatchUpdateStateServiceBO batchUpdateStateParamToServiceBO(BatchUpdateStateRequestParam source);

	//=================================================================================

	SysUser createServiceBOToEntity(SysUserCreateServiceBO source);

	List<SysUser> createServiceBOListToEntityList(List<SysUserCreateServiceBO> source);

	SysUser updateServiceBOToEntity(SysUserUpdateServiceBO source);

	List<SysUser> updateServiceBOListToEntityList(List<SysUserUpdateServiceBO> source);

	//=================================================================================

	SysUserPageQueryMapperBO pageQueryServiceBOToMapperBO(SysUserPageQueryServiceBO source);

	SysUserRealNameLikeQueryMapperBO realNameLikeQueryServiceBOToMapperBO(SysUserRealNameLikeQueryServiceBO source);


	BatchUpdateStateMapperBO batchUpdateStateServiceBOToMapperBO(BatchUpdateStateServiceBO source);

	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
