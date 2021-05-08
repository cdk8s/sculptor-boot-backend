/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysPermissionMapper.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.*;
import com.cdk8s.sculptor.pojo.bo.mapper.syspermission.SysPermissionPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.syspermission.SysPermissionPermissionCodeListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.syspermission.SysPermissionPermissionCodeMapperBO;
import com.cdk8s.sculptor.pojo.entity.SysPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysPermissionMapper extends CustomBaseMapper<SysPermission> {

	// =====================================查询业务 start=====================================

	SysPermission selectById(IdMapperBO mapperBO);


	List<SysPermission> selectByPermissionCode(SysPermissionPermissionCodeMapperBO mapperBO);


	List<SysPermission> selectByPermissionCodeList(SysPermissionPermissionCodeListMapperBO mapperBO);


	List<SysPermission> selectByParentId(ParentIdMapperBO mapperBO);

	List<SysPermission> selectByParentIdList(ParentIdListMapperBO mapperBO);


	List<SysPermission> selectByIdList(IdListMapperBO mapperBO);

	List<SysPermission> selectByPageQueryMapperBo(SysPermissionPageQueryMapperBO mapperBO);

	List<SysPermission> selectByStateEnum(BaseQueryMapperBO mapperBO);

	List<SysPermission> selectByDeleteEnum(BaseQueryMapperBO mapperBO);

	List<SysPermission> selectByDeleteEnumAndStateEnum(BaseQueryMapperBO mapperBO);

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<SysPermission> list);

	int updateStateEnumByIdList(BatchUpdateStateMapperBO mapperBO);

	int updateDeleteEnumByIdList(BatchDeleteMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
