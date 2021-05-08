/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysBannerMapper.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysbanner.*;
import com.cdk8s.sculptor.pojo.entity.SysBanner;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysBannerMapper extends CustomBaseMapper<SysBanner> {

	// =====================================查询业务 start=====================================

	SysBanner selectById(IdMapperBO mapperBO);

	List<SysBanner> selectByBannerTitleWhereLike(SysBannerBannerTitleLikeQueryMapperBO mapperBO);


	SysBanner selectOneByJumpObjectId(SysBannerJumpObjectIdMapperBO mapperBO);

	List<SysBanner> selectByJumpObjectId(SysBannerJumpObjectIdMapperBO mapperBO);

	List<SysBanner> selectByJumpObjectIdList(SysBannerJumpObjectIdListMapperBO mapperBO);


	List<SysBanner> selectByJumpTypeCode(SysBannerJumpTypeCodeMapperBO mapperBO);

	List<SysBanner> selectByBannerCode(SysBannerBannerCodeMapperBO mapperBO);


	List<SysBanner> selectByBannerCodeList(SysBannerBannerCodeListMapperBO mapperBO);


	List<SysBanner> selectByIdList(IdListMapperBO mapperBO);

	List<SysBanner> selectByPageQueryMapperBo(SysBannerPageQueryMapperBO mapperBO);


	List<SysBanner> selectByDeleteEnum(BaseQueryMapperBO mapperBO);


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<SysBanner> list);


	int updateDeleteEnumByIdList(BatchDeleteMapperBO mapperBO);

	int updateDeleteEnumByJumpObjectIdList(SysBannerJumpObjectIdListToDeleteMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
