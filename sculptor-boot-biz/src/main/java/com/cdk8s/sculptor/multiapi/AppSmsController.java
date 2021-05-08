/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：AppSmsController.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.multiapi;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.BooleanEnum;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.enums.SmsProviderTypeEnum;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.multiapi.pojo.param.AppSendLoginSmsCodeRequestParam;
import com.cdk8s.sculptor.pojo.bo.service.syssmsloginlog.SysSmsLoginLogCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysuser.SysUserMobilePhoneServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysUser;
import com.cdk8s.sculptor.service.SysSmsLoginLogService;
import com.cdk8s.sculptor.service.SysUserService;
import com.cdk8s.sculptor.service.ValidateCodeService;
import com.cdk8s.sculptor.sms.service.SmsStrategyService;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.DatetimeUtil;
import com.cdk8s.sculptor.util.RandomUtil;
import com.cdk8s.sculptor.util.UserAgentUtil;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import com.cdk8s.sculptor.util.ip.IPUtil;
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
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/multiapi")
public class AppSmsController {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private ValidateCodeService validateCodeService;

	@Autowired
	private SmsStrategyService smsStrategyService;

	@Autowired
	private SysSmsLoginLogService sysSmsLoginLogService;

	// =====================================查询业务 start=====================================

	@RequestMapping(value = "/open/sendLoginSmsCode", method = RequestMethod.POST)
	public ResponseEntity<?> login(@Valid @RequestBody AppSendLoginSmsCodeRequestParam param, HttpServletRequest request) {

		log.info("------zch------发送短信登录 param <{}>", param.toString());

		// 验证验证码是否正确
		validateCodeService.validate(param.getDeviceId(), param.getValidateCode());

		// 判断该手机号用户是否存在
		SysUserMobilePhoneServiceBO sysUserMobilePhoneServiceBO = new SysUserMobilePhoneServiceBO();
		sysUserMobilePhoneServiceBO.setMobilePhone(param.getMobilePhone());
		sysUserMobilePhoneServiceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		sysUserMobilePhoneServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		List<SysUser> listByMobilePhone = sysUserService.findListByMobilePhone(sysUserMobilePhoneServiceBO);
		if (CollectionUtil.isEmpty(listByMobilePhone)) {
			throw new BusinessException("该用户不存在，请确认您输入的手机号");
		}

		// 触发发送短信
		Long sysSmsLoginLogId = GenerateIdUtil.getId();
		String verificationCode = RandomUtil.randomNumeric(4);
		String smsTemplateCode = "SMS_116825356";

		// SmsStrategyBO smsStrategyBO = smsStrategyService.sendSmsVerificationCode(SmsTypeEnum.ALIYUN.getDescription(), param.getMobilePhone(), smsTemplateCode, verificationCode, String.valueOf(sysSmsLoginLogId));
		// if (null == smsStrategyBO) {
		// 	throw new BusinessException("发送短信失败，请联系管理员进行处理");
		// }
		// if (!smsStrategyBO.getState()) {
		// 	throw new BusinessException("发送短信失败，错误原因：" + smsStrategyBO.getMessage());
		// }

		// 写入发送记录表
		SysUser sysUser = listByMobilePhone.get(0);
		long currentEpochMilli = DatetimeUtil.currentEpochMilli();
		SysSmsLoginLogCreateServiceBO sysSmsLoginLogCreateServiceBO = new SysSmsLoginLogCreateServiceBO();
		sysSmsLoginLogCreateServiceBO.setId(sysSmsLoginLogId);
		sysSmsLoginLogCreateServiceBO.setUserId(sysUser.getId());
		sysSmsLoginLogCreateServiceBO.setUserMobilePhone(param.getMobilePhone());
		sysSmsLoginLogCreateServiceBO.setVerificationCode(verificationCode);
		sysSmsLoginLogCreateServiceBO.setSmsProviderTypeEnum(SmsProviderTypeEnum.ALIYUN.getCode());
		sysSmsLoginLogCreateServiceBO.setBoolServiceStateEnum(BooleanEnum.YES.getCode());
		sysSmsLoginLogCreateServiceBO.setMessageContent(smsTemplateCode);
		sysSmsLoginLogCreateServiceBO.setBoolUseEnum(BooleanEnum.NO.getCode());
		sysSmsLoginLogCreateServiceBO.setIpAddress(IPUtil.getIp(request));
		sysSmsLoginLogCreateServiceBO.setUserAgent(UserAgentUtil.getUserAgent(request));
		sysSmsLoginLogCreateServiceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		sysSmsLoginLogCreateServiceBO.setCreateDate(currentEpochMilli);
		sysSmsLoginLogCreateServiceBO.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		sysSmsLoginLogCreateServiceBO.setUpdateDate(currentEpochMilli);
		sysSmsLoginLogCreateServiceBO.setUpdateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		sysSmsLoginLogCreateServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		sysSmsLoginLogService.create(sysSmsLoginLogCreateServiceBO);

		return R.success();
	}


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
