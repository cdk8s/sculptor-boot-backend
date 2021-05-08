/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：RelRoleUserMapper.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.relroleuser.*;
import com.cdk8s.sculptor.pojo.entity.RelRoleUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RelRoleUserMapper extends CustomBaseMapper<RelRoleUser> {

	// =====================================查询业务 start=====================================

	RelRoleUser selectById(IdMapperBO mapperBO);


	RelRoleUser selectOneByRoleId(RelRoleUserRoleIdMapperBO mapperBO);

	List<RelRoleUser> selectByRoleId(RelRoleUserRoleIdMapperBO mapperBO);

	List<RelRoleUser> selectByRoleIdList(RelRoleUserRoleIdListMapperBO mapperBO);

	RelRoleUser selectOneByUserId(RelRoleUserUserIdMapperBO mapperBO);

	List<RelRoleUser> selectByUserId(RelRoleUserUserIdMapperBO mapperBO);

	List<RelRoleUser> selectByUserIdList(RelRoleUserUserIdListMapperBO mapperBO);


	List<RelRoleUser> selectByIdList(IdListMapperBO mapperBO);

	List<RelRoleUser> selectByPageQueryMapperBo(RelRoleUserPageQueryMapperBO mapperBO);


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<RelRoleUser> list);


	int deleteByIdList(BatchDeleteMapperBO mapperBO);

	int deleteByRoleIdAndUserId(RelRoleUserRoleIdAndUserIdToDeleteMapperBO mapperBO);

	int deleteByRoleIdList(RelRoleUserRoleIdListToDeleteMapperBO mapperBO);

	int deleteByUserIdList(RelRoleUserUserIdListToDeleteMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
