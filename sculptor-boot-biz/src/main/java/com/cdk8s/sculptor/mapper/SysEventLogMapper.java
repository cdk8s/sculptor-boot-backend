/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysEventLogMapper.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.syseventlog.*;
import com.cdk8s.sculptor.pojo.entity.SysEventLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysEventLogMapper extends CustomBaseMapper<SysEventLog> {

	// =====================================查询业务 start=====================================

	SysEventLog selectById(IdMapperBO mapperBO);


	SysEventLog selectOneByUserId(SysEventLogUserIdMapperBO mapperBO);

	List<SysEventLog> selectByUserId(SysEventLogUserIdMapperBO mapperBO);

	List<SysEventLog> selectByUserIdList(SysEventLogUserIdListMapperBO mapperBO);


	List<SysEventLog> selectByUsername(SysEventLogUsernameMapperBO mapperBO);


	List<SysEventLog> selectByUsernameList(SysEventLogUsernameListMapperBO mapperBO);


	List<SysEventLog> selectByIdList(IdListMapperBO mapperBO);

	List<SysEventLog> selectByPageQueryMapperBo(SysEventLogPageQueryMapperBO mapperBO);


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<SysEventLog> list);


	int deleteByIdList(BatchDeleteMapperBO mapperBO);

	int deleteByUserIdList(SysEventLogUserIdListToDeleteMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
