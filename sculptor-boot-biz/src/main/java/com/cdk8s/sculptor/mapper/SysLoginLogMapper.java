/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysLoginLogMapper.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysloginlog.*;
import com.cdk8s.sculptor.pojo.entity.SysLoginLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysLoginLogMapper extends CustomBaseMapper<SysLoginLog> {

	// =====================================查询业务 start=====================================

	SysLoginLog selectById(IdMapperBO mapperBO);


	SysLoginLog selectOneByUserId(SysLoginLogUserIdMapperBO mapperBO);

	List<SysLoginLog> selectByUserId(SysLoginLogUserIdMapperBO mapperBO);

	List<SysLoginLog> selectByUserIdList(SysLoginLogUserIdListMapperBO mapperBO);


	List<SysLoginLog> selectByToken(SysLoginLogTokenMapperBO mapperBO);

	List<SysLoginLog> selectByUsername(SysLoginLogUsernameMapperBO mapperBO);


	List<SysLoginLog> selectByUsernameList(SysLoginLogUsernameListMapperBO mapperBO);


	List<SysLoginLog> selectByIdList(IdListMapperBO mapperBO);

	List<SysLoginLog> selectByPageQueryMapperBo(SysLoginLogPageQueryMapperBO mapperBO);


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<SysLoginLog> list);


	int deleteByIdList(BatchDeleteMapperBO mapperBO);

	int deleteByUserIdList(SysLoginLogUserIdListToDeleteMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
