/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysRoleMapper.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.*;
import com.cdk8s.sculptor.pojo.bo.mapper.sysrole.SysRolePageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysrole.SysRoleRoleCodeListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysrole.SysRoleRoleCodeMapperBO;
import com.cdk8s.sculptor.pojo.entity.SysRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysRoleMapper extends CustomBaseMapper<SysRole> {

	// =====================================查询业务 start=====================================

	SysRole selectById(IdMapperBO mapperBO);


	List<SysRole> selectByRoleCode(SysRoleRoleCodeMapperBO mapperBO);


	List<SysRole> selectByRoleCodeList(SysRoleRoleCodeListMapperBO mapperBO);


	List<SysRole> selectByIdList(IdListMapperBO mapperBO);

	List<SysRole> selectByPageQueryMapperBo(SysRolePageQueryMapperBO mapperBO);

	List<SysRole> selectByStateEnum(BaseQueryMapperBO mapperBO);

	List<SysRole> selectByDeleteEnum(BaseQueryMapperBO mapperBO);

	List<SysRole> selectByDeleteEnumAndStateEnum(BaseQueryMapperBO mapperBO);

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<SysRole> list);

	int updateStateEnumByIdList(BatchUpdateStateMapperBO mapperBO);

	int updateDeleteEnumByIdList(BatchDeleteMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
