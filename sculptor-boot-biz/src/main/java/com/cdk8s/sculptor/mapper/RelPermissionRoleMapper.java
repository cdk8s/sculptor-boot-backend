/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：RelPermissionRoleMapper.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.relpermissionrole.*;
import com.cdk8s.sculptor.pojo.entity.RelPermissionRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RelPermissionRoleMapper extends CustomBaseMapper<RelPermissionRole> {

	// =====================================查询业务 start=====================================

	RelPermissionRole selectById(IdMapperBO mapperBO);


	RelPermissionRole selectOneByPermissionId(RelPermissionRolePermissionIdMapperBO mapperBO);

	List<RelPermissionRole> selectByPermissionId(RelPermissionRolePermissionIdMapperBO mapperBO);

	List<RelPermissionRole> selectByPermissionIdList(RelPermissionRolePermissionIdListMapperBO mapperBO);

	RelPermissionRole selectOneByRoleId(RelPermissionRoleRoleIdMapperBO mapperBO);

	List<RelPermissionRole> selectByRoleId(RelPermissionRoleRoleIdMapperBO mapperBO);

	List<RelPermissionRole> selectByRoleIdList(RelPermissionRoleRoleIdListMapperBO mapperBO);


	List<RelPermissionRole> selectByIdList(IdListMapperBO mapperBO);

	List<RelPermissionRole> selectByPageQueryMapperBo(RelPermissionRolePageQueryMapperBO mapperBO);


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<RelPermissionRole> list);


	int deleteByIdList(BatchDeleteMapperBO mapperBO);

	int deleteByPermissionIdAndRoleId(RelPermissionRolePermissionIdAndRoleIdToDeleteMapperBO mapperBO);

	int deleteByPermissionIdList(RelPermissionRolePermissionIdListToDeleteMapperBO mapperBO);

	int deleteByRoleIdList(RelPermissionRoleRoleIdListToDeleteMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
