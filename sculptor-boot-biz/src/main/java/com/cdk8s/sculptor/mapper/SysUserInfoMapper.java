/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysUserInfoMapper.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysuserinfo.*;
import com.cdk8s.sculptor.pojo.entity.SysUserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysUserInfoMapper extends CustomBaseMapper<SysUserInfo> {

	// =====================================查询业务 start=====================================

	SysUserInfo selectById(IdMapperBO mapperBO);


	SysUserInfo selectOneByUserId(SysUserInfoUserIdMapperBO mapperBO);

	List<SysUserInfo> selectByUserId(SysUserInfoUserIdMapperBO mapperBO);

	List<SysUserInfo> selectByUserIdList(SysUserInfoUserIdListMapperBO mapperBO);


	List<SysUserInfo> selectByWeixinUnionid(SysUserInfoWeixinUnionidMapperBO mapperBO);

	List<SysUserInfo> selectByWeixinOpenid(SysUserInfoWeixinOpenidMapperBO mapperBO);

	List<SysUserInfo> selectByIdCard(SysUserInfoIdCardMapperBO mapperBO);


	List<SysUserInfo> selectByWeixinUnionidList(SysUserInfoWeixinUnionidListMapperBO mapperBO);

	List<SysUserInfo> selectByWeixinOpenidList(SysUserInfoWeixinOpenidListMapperBO mapperBO);

	List<SysUserInfo> selectByIdCardList(SysUserInfoIdCardListMapperBO mapperBO);


	List<SysUserInfo> selectByIdList(IdListMapperBO mapperBO);

	List<SysUserInfo> selectByPageQueryMapperBo(SysUserInfoPageQueryMapperBO mapperBO);


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<SysUserInfo> list);


	int deleteByIdList(BatchDeleteMapperBO mapperBO);


	int deleteByUserIdList(SysUserInfoUserIdListToDeleteMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
