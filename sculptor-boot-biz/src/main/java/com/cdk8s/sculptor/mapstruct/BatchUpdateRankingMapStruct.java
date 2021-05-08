/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：BatchUpdateRankingMapStruct.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapstruct;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchUpdateRankingMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchUpdateRankingServiceBO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@SuppressWarnings("all")
@Component
@Mapper(componentModel = "spring")
public interface BatchUpdateRankingMapStruct {

	BatchUpdateRankingMapperBO updateRankingServiceBOToMapperBO(BatchUpdateRankingServiceBO source);

	List<BatchUpdateRankingMapperBO> batchUpdateRankingServiceBOToMapperBO(List<BatchUpdateRankingServiceBO> source);

	//=================================================================================

}
