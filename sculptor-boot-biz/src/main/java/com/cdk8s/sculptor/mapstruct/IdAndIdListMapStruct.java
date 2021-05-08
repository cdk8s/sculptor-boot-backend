/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：IdAndIdListMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdRequestParam;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface IdAndIdListMapStruct {

	IdServiceBO requestParamToServiceBO(IdRequestParam source);

	IdMapperBO serviceBOToMapperBO(IdServiceBO source);

	IdListServiceBO requestParamToServiceBO(IdListRequestParam source);

	IdListMapperBO serviceBOToMapperBO(IdListServiceBO source);

	//=================================================================================

}
