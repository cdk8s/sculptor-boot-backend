package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysloginlog.SysLoginLogPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysloginlog.SysLoginLogCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysloginlog.SysLoginLogPageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysloginlog.SysLoginLogUpdateServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysloginlog.SysLoginLogCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysloginlog.SysLoginLogPageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.sysloginlog.SysLoginLogUpdateRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.sysloginlog.SysLoginLogResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysLoginLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface SysLoginLogMapStruct {

	@Mappings({
			@Mapping(target = "boolLoginSuccessEnumString", expression = "java(com.cdk8s.sculptor.enums.BooleanEnum.getDescriptionByCode(source.getBoolLoginSuccessEnum()))"),
			@Mapping(target = "boolNowOnlineEnumString", expression = "java(com.cdk8s.sculptor.enums.BooleanEnum.getDescriptionByCode(source.getBoolNowOnlineEnum()))"),
			@Mapping(target = "offlineTypeEnumString", expression = "java(com.cdk8s.sculptor.enums.OfflineTypeEnum.getDescriptionByCode(source.getOfflineTypeEnum()))"),
			@Mapping(target = "boolNewUserEnumString", expression = "java(com.cdk8s.sculptor.enums.BooleanEnum.getDescriptionByCode(source.getBoolNewUserEnum()))"),
			@Mapping(target = "loginOriginEnumString", expression = "java(com.cdk8s.sculptor.enums.LoginOriginEnum.getDescriptionByCode(source.getLoginOriginEnum()))"),
	})
	SysLoginLogResponseDTO toResponseDTO(SysLoginLog source);

	List<SysLoginLogResponseDTO> toResponseDTOList(List<SysLoginLog> source);

	//=================================================================================

	SysLoginLogPageQueryServiceBO pageQueryParamToServiceBO(SysLoginLogPageQueryParam source);

	SysLoginLogCreateServiceBO createRequestParamToServiceBO(SysLoginLogCreateRequestParam source);

	SysLoginLogUpdateServiceBO updateRequestParamToServiceBO(SysLoginLogUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);


	//=================================================================================

	SysLoginLog createServiceBOToEntity(SysLoginLogCreateServiceBO source);

	List<SysLoginLog> createServiceBOListToEntityList(List<SysLoginLogCreateServiceBO> source);

	SysLoginLog updateServiceBOToEntity(SysLoginLogUpdateServiceBO source);

	List<SysLoginLog> updateServiceBOListToEntityList(List<SysLoginLogUpdateServiceBO> source);

	//=================================================================================

	SysLoginLogPageQueryMapperBO pageQueryServiceBOToMapperBO(SysLoginLogPageQueryServiceBO source);


	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
