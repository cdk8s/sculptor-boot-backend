/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：RelDeptUserService.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.mapper.RelDeptUserMapper;
import com.cdk8s.sculptor.mapper.ext.RelDeptUserMapperExt;
import com.cdk8s.sculptor.mapstruct.RelDeptUserMapStruct;
import com.cdk8s.sculptor.service.bases.RelDeptUserServiceBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RelDeptUserService extends RelDeptUserServiceBase {

	@Autowired
	private RelDeptUserMapper relDeptUserMapper;

	@Autowired
	private RelDeptUserMapperExt relDeptUserMapperExt;

	@Autowired
	private RelDeptUserMapStruct relDeptUserMapStruct;

	// =====================================查询业务 start=====================================


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}

