/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：UserIdAndUserIdListMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.DeleteByUserIdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.DeleteByUserIdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.UserIdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.UserIdServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.bases.UserIdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.UserIdRequestParam;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface UserIdAndUserIdListMapStruct {

	UserIdServiceBO requestParamToServiceBO(UserIdRequestParam source);

	UserIdListServiceBO requestParamToServiceBO(UserIdListRequestParam source);

	DeleteByUserIdListMapperBO serviceBOToMapperBO(DeleteByUserIdListServiceBO serviceBO);

	//=================================================================================

}
