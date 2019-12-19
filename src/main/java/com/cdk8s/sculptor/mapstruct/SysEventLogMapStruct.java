package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.syseventlog.SysEventLogPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syseventlog.SysEventLogCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syseventlog.SysEventLogPageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syseventlog.SysEventLogUpdateServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.syseventlog.SysEventLogCreateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.syseventlog.SysEventLogPageQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.syseventlog.SysEventLogUpdateRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.syseventlog.SysEventLogResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysEventLog;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface SysEventLogMapStruct {

	@Mappings({
			@Mapping(target = "boolExecuteSuccessEnumString", expression = "java(com.cdk8s.sculptor.enums.BooleanEnum.getDescriptionByCode(source.getBoolExecuteSuccessEnum()))"),
			@Mapping(target = "operateTypeEnumString", expression = "java(com.cdk8s.sculptor.enums.OperateTypeEnum.getDescriptionByCode(source.getOperateTypeEnum()))"),
	})
	SysEventLogResponseDTO toResponseDTO(SysEventLog source);

	List<SysEventLogResponseDTO> toResponseDTOList(List<SysEventLog> source);

	//=================================================================================

	SysEventLogPageQueryServiceBO pageQueryParamToServiceBO(SysEventLogPageQueryParam source);

	SysEventLogCreateServiceBO createRequestParamToServiceBO(SysEventLogCreateRequestParam source);

	SysEventLogUpdateServiceBO updateRequestParamToServiceBO(SysEventLogUpdateRequestParam source);

	BatchDeleteServiceBO batchDeleteParamToServiceBO(BatchDeleteRequestParam source);


	//=================================================================================

	SysEventLog createServiceBOToEntity(SysEventLogCreateServiceBO source);

	List<SysEventLog> createServiceBOListToEntityList(List<SysEventLogCreateServiceBO> source);

	SysEventLog updateServiceBOToEntity(SysEventLogUpdateServiceBO source);

	List<SysEventLog> updateServiceBOListToEntityList(List<SysEventLogUpdateServiceBO> source);

	//=================================================================================

	SysEventLogPageQueryMapperBO pageQueryServiceBOToMapperBO(SysEventLogPageQueryServiceBO source);


	BatchDeleteMapperBO batchDeleteServiceBOToMapperBO(BatchDeleteServiceBO source);

	//=================================================================================

}
