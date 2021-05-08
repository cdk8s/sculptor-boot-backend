/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：AppIndexController.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.multiapi;

import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysUser;
import com.cdk8s.sculptor.service.SysUserService;
import com.cdk8s.sculptor.strategy.UserInfoContext;
import com.cdk8s.sculptor.util.response.biz.R;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
@RequestMapping("/multiapi")
public class AppIndexController {

	@Autowired
	private SysUserService sysUserService;

	// =====================================查询业务 start=====================================

	@SneakyThrows
	@RequestMapping(value = "/open/index", method = RequestMethod.POST)
	public ResponseEntity<?> index() {
		TimeUnit.SECONDS.sleep(6);
		Long currentUserId = UserInfoContext.getCurrentUserId();
		if (null != currentUserId) {
			IdServiceBO idServiceBO = new IdServiceBO();
			idServiceBO.setId(currentUserId);
			idServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
			SysUser entity = sysUserService.findOneById(idServiceBO);
			return R.success(entity);
		}
		return R.success();
	}


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================


	// =====================================私有方法 end=====================================
}
