/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysJobLogMapper.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysjoblog.*;
import com.cdk8s.sculptor.pojo.entity.SysJobLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysJobLogMapper extends CustomBaseMapper<SysJobLog> {

	// =====================================查询业务 start=====================================

	SysJobLog selectById(IdMapperBO mapperBO);


	SysJobLog selectOneByJobId(SysJobLogJobIdMapperBO mapperBO);

	List<SysJobLog> selectByJobId(SysJobLogJobIdMapperBO mapperBO);

	List<SysJobLog> selectByJobIdList(SysJobLogJobIdListMapperBO mapperBO);


	List<SysJobLog> selectByJobName(SysJobLogJobNameMapperBO mapperBO);


	List<SysJobLog> selectByJobNameList(SysJobLogJobNameListMapperBO mapperBO);


	List<SysJobLog> selectByIdList(IdListMapperBO mapperBO);

	List<SysJobLog> selectByPageQueryMapperBo(SysJobLogPageQueryMapperBO mapperBO);


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<SysJobLog> list);


	int deleteByIdList(BatchDeleteMapperBO mapperBO);

	int deleteByJobIdList(SysJobLogJobIdListToDeleteMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
