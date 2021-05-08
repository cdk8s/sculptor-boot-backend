/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：ParentIdAndParentIdListMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.ParentIdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.ParentIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.ParentIdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.ParentIdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.TreeCascadeServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.bases.ParentIdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.ParentIdRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.TreeCascadeRequestParam;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface ParentIdAndParentIdListMapStruct {

	ParentIdServiceBO requestParamToServiceBO(ParentIdRequestParam source);

	ParentIdMapperBO serviceBOToMapperBO(ParentIdServiceBO source);

	TreeCascadeServiceBO requestParamToServiceBO(TreeCascadeRequestParam source);

	ParentIdListServiceBO requestParamToServiceBO(ParentIdListRequestParam source);

	ParentIdListMapperBO serviceBOToMapperBO(ParentIdListServiceBO source);

	//=================================================================================

}
