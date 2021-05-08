/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：BatchUpdateBooleanEnumMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchUpdateBooleanEnumMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateBooleanEnumServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateBooleanEnumRequestParam;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface BatchUpdateBooleanEnumMapStruct {


	@Mappings({
			@Mapping(target = "updateDate", expression = "java(com.cdk8s.sculptor.util.DatetimeUtil.currentEpochMilli())"),
			@Mapping(target = "updateUserId", expression = "java(com.cdk8s.sculptor.strategy.UserInfoContext.getCurrentUserId())"),
	})
	BatchUpdateBooleanEnumServiceBO batchUpdateBooleanEnumParamToServiceBO(BatchUpdateBooleanEnumRequestParam source);


	BatchUpdateBooleanEnumMapperBO batchUpdateBooleanEnumServiceBOToMapperBO(BatchUpdateBooleanEnumServiceBO source);

	//=================================================================================

}
