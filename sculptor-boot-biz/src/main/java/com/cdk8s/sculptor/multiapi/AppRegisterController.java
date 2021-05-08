/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：AppRegisterController.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.multiapi;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.RegisterOriginEnum;
import com.cdk8s.sculptor.enums.RegisterTypeEnum;
import com.cdk8s.sculptor.enums.UserTypeEnum;
import com.cdk8s.sculptor.multiapi.pojo.param.AppRegisterRequestParam;
import com.cdk8s.sculptor.pojo.bo.service.sysuser.SysUserCreateServiceBO;
import com.cdk8s.sculptor.service.SysUserService;
import com.cdk8s.sculptor.service.ValidateCodeService;
import com.cdk8s.sculptor.util.DatetimeUtil;
import com.cdk8s.sculptor.util.response.biz.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/multiapi")
public class AppRegisterController {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private ValidateCodeService validateCodeService;

	// =====================================查询业务 start=====================================

	@RequestMapping(value = "/open/register", method = RequestMethod.POST)
	public ResponseEntity<?> register(@Valid @RequestBody AppRegisterRequestParam param, HttpServletRequest request) {
		log.info("------zch------发起注册 param <{}>", param.toString());

		// 验证验证码是否正确
		validateCodeService.validate(param.getDeviceId(), param.getValidateCode());

		SysUserCreateServiceBO sysUserCreateServiceBO = new SysUserCreateServiceBO();
		sysUserCreateServiceBO.setUsername(param.getUsername());
		sysUserCreateServiceBO.setNickname(param.getUsername());
		sysUserCreateServiceBO.setRealName(param.getUsername());
		sysUserCreateServiceBO.setUserPassword(param.getPassword());
		sysUserCreateServiceBO.setUserEmail(param.getUsername() + "@qq.com");
		sysUserCreateServiceBO.setTelephone("13800000000");
		sysUserCreateServiceBO.setMobilePhone("13800000000");
		sysUserCreateServiceBO.setAvatarUrl("http://pic.616pic.com/ys_bnew_img/00/08/69/fTQoNQUpok.jpg");
		sysUserCreateServiceBO.setUserTypeEnum(UserTypeEnum.USER.getCode());
		sysUserCreateServiceBO.setRegisterTypeEnum(RegisterTypeEnum.REGISTER.getCode());
		sysUserCreateServiceBO.setRegisterOriginEnum(RegisterOriginEnum.getRegisterOriginEnumCode(request).getCode());
		Long currentEpochMilli = DatetimeUtil.currentEpochMilli();
		sysUserCreateServiceBO.setCreateDate(currentEpochMilli);
		sysUserCreateServiceBO.setUpdateDate(currentEpochMilli);
		sysUserCreateServiceBO.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		sysUserCreateServiceBO.setUpdateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		sysUserService.create(sysUserCreateServiceBO);

		return R.success();
	}


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
