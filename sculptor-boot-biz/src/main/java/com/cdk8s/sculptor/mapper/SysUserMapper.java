/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysUserMapper.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.*;
import com.cdk8s.sculptor.pojo.bo.mapper.sysuser.*;
import com.cdk8s.sculptor.pojo.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysUserMapper extends CustomBaseMapper<SysUser> {

	// =====================================查询业务 start=====================================

	SysUser selectById(IdMapperBO mapperBO);

	List<SysUser> selectByRealNameWhereLike(SysUserRealNameLikeQueryMapperBO mapperBO);


	List<SysUser> selectByUsername(SysUserUsernameMapperBO mapperBO);

	List<SysUser> selectByUserEmail(SysUserUserEmailMapperBO mapperBO);

	List<SysUser> selectByNickname(SysUserNicknameMapperBO mapperBO);

	List<SysUser> selectByMobilePhone(SysUserMobilePhoneMapperBO mapperBO);


	List<SysUser> selectByIdList(IdListMapperBO mapperBO);

	List<SysUser> selectByPageQueryMapperBo(SysUserPageQueryMapperBO mapperBO);

	List<SysUser> selectByStateEnum(BaseQueryMapperBO mapperBO);

	List<SysUser> selectByDeleteEnum(BaseQueryMapperBO mapperBO);

	List<SysUser> selectByDeleteEnumAndStateEnum(BaseQueryMapperBO mapperBO);

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<SysUser> list);

	int updateStateEnumByIdList(BatchUpdateStateMapperBO mapperBO);

	int updateDeleteEnumByIdList(BatchDeleteMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
