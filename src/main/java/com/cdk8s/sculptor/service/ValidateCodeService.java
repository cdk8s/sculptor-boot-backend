package com.cdk8s.sculptor.service;


import cn.hutool.core.util.RandomUtil;
import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.exception.ValidateCodeException;
import com.cdk8s.sculptor.pojo.entity.SysUser;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.redis.StringRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;


@Slf4j
@Service
public class ValidateCodeService {

	@Autowired
	private StringRedisService<String, String> stringRedisService;

	@Autowired
	private SysUserService sysUserService;

	// =====================================查询业务 start=====================================

	/**
	 * 获取验证码
	 *
	 * @param deviceId 前端唯一标识/手机号
	 */
	public String getValidateCode(String deviceId) {
		return stringRedisService.get(buildRedisKey(deviceId));
	}

	/**
	 * 验证验证码
	 */
	public void validate(String deviceId, String inputValidateCode) {
		if (StringUtil.isBlank(inputValidateCode)) {
			throw new ValidateCodeException("请填写验证码");
		}

		if (StringUtil.isBlank(deviceId)) {
			throw new ValidateCodeException("请在请求参数中携带deviceId参数");
		}

		String validateCode = getValidateCode(deviceId);
		if (StringUtil.isBlank(validateCode)) {
			throw new ValidateCodeException("验证码已过期");
		}

		if (StringUtil.notEqualsIgnoreCase(validateCode, inputValidateCode)) {
			throw new ValidateCodeException("验证码不正确");
		}

		// 验证正确，删除已验证
		removeValidateCode(deviceId);
	}

	// =====================================查询业务 end=====================================

	/**
	 * 保存用户验证码，和randomStr绑定
	 *
	 * @param deviceId  客户端生成
	 * @param imageCode 验证码信息
	 */
	public void saveImageCode(String deviceId, String imageCode) {
		stringRedisService.set(buildRedisKey(deviceId), imageCode, GlobalConstant.DEFAULT_VALIDATE_CODE_EXPIRE_TIME_SECOND, TimeUnit.SECONDS);
	}

	/**
	 * 发送短信验证码
	 */
	public void sendSmsCode(String mobilePhone) {
		String smsCode = stringRedisService.get(buildRedisKey(mobilePhone));
		if (StringUtil.isNotBlank(smsCode)) {
			throw new BusinessException("旧短信验证码还未失效，请失效后再次申请");
		}

		List<SysUser> listByMobilePhone = sysUserService.findListByMobilePhone(mobilePhone);
		if (CollectionUtil.isEmpty(listByMobilePhone)) {
			log.error("根据用户手机号 {} 查询用户为空", mobilePhone);
			throw new BusinessException("手机号不存在");
		}
		if (listByMobilePhone.size() > 1) {
			log.error("根据用户手机号 {} 查询用户有 {} 个", mobilePhone, listByMobilePhone.size());
			throw new BusinessException("手机号存在多个，请联系管理员");
		}

		String code = RandomUtil.randomNumbers(4);
		log.info("短信发送请求消息中心 -> 手机号:{} -> 验证码：{}", mobilePhone, code);
		stringRedisService.set(buildRedisKey(mobilePhone), code, GlobalConstant.DEFAULT_VALIDATE_CODE_EXPIRE_TIME_SECOND, TimeUnit.SECONDS);
	}


	/**
	 * 删除验证码
	 */
	public void removeValidateCode(String deviceId) {
		stringRedisService.delete(buildRedisKey(deviceId));
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private String buildRedisKey(String deviceId) {
		return GlobalConstant.REDIS_VALIDATE_CODE_KEY_PREFIX + deviceId;
	}

	// =====================================私有方法 end=====================================
}

