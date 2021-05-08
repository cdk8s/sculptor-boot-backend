/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：RelRoleUserController.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.controller;

import com.cdk8s.sculptor.aop.eventlog.EventLog;
import com.cdk8s.sculptor.aop.eventlog.EventLogEnum;
import com.cdk8s.sculptor.controller.bases.RelRoleUserControllerBase;
import com.cdk8s.sculptor.mapstruct.RelRoleUserMapStruct;
import com.cdk8s.sculptor.pojo.bo.service.relroleuser.RelRoleUserUserIdListToDeleteServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.relroleuser.RelRoleUserUserIdListToDeleteRequestParam;
import com.cdk8s.sculptor.service.RelRoleUserService;
import com.cdk8s.sculptor.util.response.biz.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/relRoleUser")
public class RelRoleUserController extends RelRoleUserControllerBase {

	@Autowired
	private RelRoleUserService relRoleUserService;

	@Autowired
	private RelRoleUserMapStruct relRoleUserMapStruct;

	// =====================================查询业务 start=====================================


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================
	@EventLog(message = "批量删除 relRoleUser 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestMapping(value = "/batchDeleteByUserIdList", method = RequestMethod.POST)
	public ResponseEntity<?> batchDeleteByUserIdList(@Valid @RequestBody RelRoleUserUserIdListToDeleteRequestParam param) {
		RelRoleUserUserIdListToDeleteServiceBO serviceBO = relRoleUserMapStruct.userIdListToDeleteRequestParamToServiceBO(param);
		relRoleUserService.batchDeleteByUserIdList(serviceBO);
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
