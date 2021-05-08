/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysBigTextMapper.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.*;
import com.cdk8s.sculptor.pojo.bo.mapper.sysbigtext.SysBigTextPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysbigtext.SysBigTextTextCodeListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysbigtext.SysBigTextTextCodeMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysbigtext.SysBigTextTextTitleLikeQueryMapperBO;
import com.cdk8s.sculptor.pojo.entity.SysBigText;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysBigTextMapper extends CustomBaseMapper<SysBigText> {

	// =====================================查询业务 start=====================================

	SysBigText selectById(IdMapperBO mapperBO);

	List<SysBigText> selectByTextTitleWhereLike(SysBigTextTextTitleLikeQueryMapperBO mapperBO);


	List<SysBigText> selectByTextCode(SysBigTextTextCodeMapperBO mapperBO);


	List<SysBigText> selectByTextCodeList(SysBigTextTextCodeListMapperBO mapperBO);


	List<SysBigText> selectByIdList(IdListMapperBO mapperBO);

	List<SysBigText> selectByPageQueryMapperBo(SysBigTextPageQueryMapperBO mapperBO);

	List<SysBigText> selectByStateEnum(BaseQueryMapperBO mapperBO);

	List<SysBigText> selectByDeleteEnum(BaseQueryMapperBO mapperBO);

	List<SysBigText> selectByDeleteEnumAndStateEnum(BaseQueryMapperBO mapperBO);

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<SysBigText> list);

	int updateStateEnumByIdList(BatchUpdateStateMapperBO mapperBO);

	int updateDeleteEnumByIdList(BatchDeleteMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
