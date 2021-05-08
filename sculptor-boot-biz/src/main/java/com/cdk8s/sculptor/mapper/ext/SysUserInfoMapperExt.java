/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysUserInfoMapperExt.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapper.ext;

import com.cdk8s.sculptor.pojo.bo.mapper.sysuserinfo.SysUserInfoPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.entity.SysUserInfo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysUserInfoMapperExt {

	// =====================================查询业务 start=====================================

	List<SysUserInfo> selectByPageQueryMapperBoExt(SysUserInfoPageQueryMapperBO mapperBO);

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	// =====================================操作业务 end=====================================

}
