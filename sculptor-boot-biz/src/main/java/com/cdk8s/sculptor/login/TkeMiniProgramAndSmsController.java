/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：TkeMiniProgramAndSmsController.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.login;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.constant.GlobalConstantToJunit;
import com.cdk8s.sculptor.enums.BooleanEnum;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.enums.LoginOriginEnum;
import com.cdk8s.sculptor.enums.OfflineTypeEnum;
import com.cdk8s.sculptor.eventlistener.event.LoginLogEvent;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.pojo.bo.handle.oauth.OauthTokenStrategyHandleBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysloginlog.SysLoginLogCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syssmsloginlog.SysSmsLoginLogPageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syssmsloginlog.SysSmsLoginLogUpdateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysuser.SysUserMobilePhoneServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysuser.SysUserUpdateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysuserinfo.SysUserInfoCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysuserinfo.SysUserInfoUserIdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysuserinfo.SysUserInfoWeixinOpenidServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.oauth.OauthMiniProgramAndSmsParam;
import com.cdk8s.sculptor.pojo.dto.param.oauth.OauthMiniProgramByOnlyCodeParam;
import com.cdk8s.sculptor.pojo.dto.param.oauth.OauthMiniProgramParam;
import com.cdk8s.sculptor.pojo.dto.param.oauth.OauthTokenParam;
import com.cdk8s.sculptor.pojo.dto.response.oauth.OauthToken;
import com.cdk8s.sculptor.pojo.entity.SysSmsLoginLog;
import com.cdk8s.sculptor.pojo.entity.SysUser;
import com.cdk8s.sculptor.pojo.entity.SysUserInfo;
import com.cdk8s.sculptor.properties.OauthClientProperties;
import com.cdk8s.sculptor.service.SysSmsLoginLogService;
import com.cdk8s.sculptor.service.SysUserInfoService;
import com.cdk8s.sculptor.service.SysUserService;
import com.cdk8s.sculptor.strategy.OauthTokenStrategyContext;
import com.cdk8s.sculptor.util.*;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import com.cdk8s.sculptor.util.ip.IPUtil;
import com.cdk8s.sculptor.util.redis.StringRedisService;
import com.cdk8s.sculptor.util.response.biz.R;
import com.cdk8s.sculptor.util.response.biz.ResultCodeEnum;
import com.cdk8s.sculptor.weixin.pojo.response.MiniProgramCodeToSessionResponse;
import com.cdk8s.sculptor.weixin.service.MiniProgramService;
import com.cdk8s.tkey.client.rest.pojo.dto.TkeyToken;
import com.cdk8s.tkey.client.rest.service.TkeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/oauth")
public class TkeMiniProgramAndSmsController {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private OauthTokenStrategyContext oauthTokenStrategyContext;

	@Autowired
	private TkeyService tkeyService;

	@Autowired
	private SysSmsLoginLogService sysSmsLoginLogService;

	@Autowired
	private OauthClientProperties oauthClientProperties;

	@Autowired
	private StringRedisService<String, TkeyToken> tokenRedisService;

	@Autowired
	private SysUserInfoService sysUserInfoService;

	@Autowired
	private MiniProgramService miniProgramService;

	@Autowired
	private ApplicationContext applicationContext;

	// =====================================查询业务 start=====================================

	/**
	 * 腾讯小程序文档：
	 * https://developers.weixin.qq.com/miniprogram/dev/framework/open-ability/login.html
	 * https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html
	 */
	@RequestMapping(value = "/miniProgramByOnlyCode", method = RequestMethod.POST)
	public ResponseEntity<?> miniProgramByOnlyCode(@Valid @RequestBody OauthMiniProgramByOnlyCodeParam param, HttpServletRequest request) {

		log.info("------zch------小程序 code 登录, code = <{}>", param.getCode());

		// 通过 code 拿到用户 openid
		MiniProgramCodeToSessionResponse miniProgramCodeToSessionResponse = miniProgramService.codeToSession(param.getCode());

		// 查询是否已经绑定了 openid
		SysUserInfoWeixinOpenidServiceBO sysUserInfoWechatOpenidServiceBO = new SysUserInfoWeixinOpenidServiceBO();
		sysUserInfoWechatOpenidServiceBO.setWeixinOpenid(miniProgramCodeToSessionResponse.getOpenid());
		sysUserInfoWechatOpenidServiceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		List<SysUserInfo> sysUserInfoList = sysUserInfoService.findListByWeixinOpenid(sysUserInfoWechatOpenidServiceBO);

		if (CollectionUtil.isEmpty(sysUserInfoList)) {
			// 如果未绑定，前端需要跳转到绑定手机页面
			log.error("------zch------未绑定微信，请先用手机号登录 <{}>", miniProgramCodeToSessionResponse.getOpenid());
			return R.failure(HttpStatus.BAD_REQUEST, ResultCodeEnum.NOT_BINDING_WECHAT, "未绑定微信，请先用手机号登录");
		}

		// 如果已经绑定，查询用户ID
		SysUserInfo sysUserInfo = sysUserInfoList.get(0);
		Long userId = sysUserInfo.getUserId();

		SysUser sysUser = sysUserService.findOneById(new IdServiceBO(userId));
		if (null == sysUser) {
			throw new BusinessException("该用户不存在，请重新登录");
		}

		OauthToken oauthToken = doLogin(sysUser.getId(), sysUser.getUsername(), request);
		if (null == oauthToken) {
			log.error("------zch------登录失败，请联系管理员进行处理 <{}>", param.getCode());
			return ResponseEntity.badRequest().body("登录失败，请联系管理员进行处理");
		}

		return ResponseEntity.ok(oauthToken);
	}

	/**
	 * 前端界面：
	 * 输入手机号
	 * 输入图形验证码
	 * 输入短信验证码，发送短信
	 * 其中只有发送短信按钮默认是灰色的，必须输入手机，图形验证码才能可点击。点击的时候要请求验证码是否正确，正确才能触发发送短信请求。
	 *
	 * @param param
	 * @return
	 */
	@RequestMapping(value = "/miniProgramAndSms", method = RequestMethod.POST)
	public ResponseEntity<?> login(@Valid @RequestBody OauthMiniProgramAndSmsParam param, HttpServletRequest request) {
		log.info("------zch------小程序手机短信登录, param = <{}>", param.toString());

		// 通过手机号查询用户信息，验证手机号是否存在，如果不存在则提示登录失败
		SysUserMobilePhoneServiceBO sysUserMobilePhoneServiceBO = new SysUserMobilePhoneServiceBO();
		sysUserMobilePhoneServiceBO.setMobilePhone(param.getMobilePhone());
		sysUserMobilePhoneServiceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		sysUserMobilePhoneServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		List<SysUser> sysUserList = sysUserService.findListByMobilePhone(sysUserMobilePhoneServiceBO);
		if (CollectionUtil.isEmpty(sysUserList)) {
			throw new BusinessException("该用户不存在，请确认您输入的手机号");
		}

		// 验证手机号与短信验证码是否匹配，如果不匹配提示短信验证码错误
		SysSmsLoginLogPageQueryServiceBO sysSmsLoginLogPageQueryServiceBO = new SysSmsLoginLogPageQueryServiceBO();
		sysSmsLoginLogPageQueryServiceBO.setUserMobilePhone(param.getMobilePhone());
		sysSmsLoginLogPageQueryServiceBO.setVerificationCode(param.getVerificationCode());
		sysSmsLoginLogPageQueryServiceBO.setBoolUseEnum(BooleanEnum.NO.getCode());
		sysSmsLoginLogPageQueryServiceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		List<SysSmsLoginLog> listByServiceBO = sysSmsLoginLogService.findListByServiceBO(sysSmsLoginLogPageQueryServiceBO);
		if (CollectionUtil.isEmpty(listByServiceBO)) {
			throw new BusinessException("短信验证码不正确，请重新输入");
		}

		long currentEpochMilli = DatetimeUtil.currentEpochMilli();
		SysSmsLoginLog sysSmsLoginLog = listByServiceBO.get(0);
		if (DatetimeUtil.between(sysSmsLoginLog.getCreateDate(), currentEpochMilli, ChronoUnit.SECONDS) > GlobalConstant.DEFAULT_SMS_CODE_EXPIRE_TIME_SECOND) {
			throw new BusinessException("短信验证码以超过3分钟有效期，请重新输入");
		}

		// 更新短信发送记录为已经被使用
		SysSmsLoginLogUpdateServiceBO sysSmsLoginLogUpdateServiceBO = new SysSmsLoginLogUpdateServiceBO();
		sysSmsLoginLogUpdateServiceBO.setId(sysSmsLoginLog.getId());
		sysSmsLoginLogUpdateServiceBO.setBoolUseEnum(BooleanEnum.YES.getCode());
		sysSmsLoginLogUpdateServiceBO.setUpdateDate(currentEpochMilli);
		sysSmsLoginLogUpdateServiceBO.setUpdateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		sysSmsLoginLogService.update(sysSmsLoginLogUpdateServiceBO);

		// 更新微信信息到用户表
		SysUser sysUser = sysUserList.get(0);
		OauthMiniProgramParam.UserInfoBean paramUserInfo = param.getUserInfo();
		SysUserUpdateServiceBO sysUserUpdateServiceBO = new SysUserUpdateServiceBO();
		sysUserUpdateServiceBO.setId(sysUser.getId());
		sysUserUpdateServiceBO.setNickname(paramUserInfo.getNickName());
		sysUserUpdateServiceBO.setAvatarUrl(paramUserInfo.getAvatarUrl());
		sysUserUpdateServiceBO.setGenderEnum(paramUserInfo.getGender());
		sysUserUpdateServiceBO.setUpdateDate(currentEpochMilli);
		sysUserUpdateServiceBO.setUpdateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		sysUserService.update(sysUserUpdateServiceBO);

		// 通过 code 拿到用户 openid
		MiniProgramCodeToSessionResponse miniProgramCodeToSessionResponse = miniProgramService.codeToSession(param.getMiniProgramCode());

		// 绑定用户信息表
		SysUserInfoUserIdServiceBO sysUserInfoUserIdServiceBO = new SysUserInfoUserIdServiceBO();
		sysUserInfoUserIdServiceBO.setUserId(sysUser.getId());
		SysUserInfo sysUserInfo = sysUserInfoService.findOneByUserId(sysUserInfoUserIdServiceBO);
		if (null == sysUserInfo) {
			// 创建一条记录
			SysUserInfoCreateServiceBO sysUserInfoCreateServiceBO = new SysUserInfoCreateServiceBO();
			sysUserInfoCreateServiceBO.setId(GenerateIdUtil.getId());
			sysUserInfoCreateServiceBO.setUserId(sysUser.getId());
			sysUserInfoCreateServiceBO.setWeixinOpenid(miniProgramCodeToSessionResponse.getOpenid());
			sysUserInfoCreateServiceBO.setWeixinUnionid(miniProgramCodeToSessionResponse.getUnionid());
			sysUserInfoCreateServiceBO.setWeixinUserinfo(JsonUtil.toJson(miniProgramCodeToSessionResponse));
			sysUserInfoCreateServiceBO.setTenantId(sysUser.getTenantId());
			sysUserInfoCreateServiceBO.setCreateDate(currentEpochMilli);
			sysUserInfoCreateServiceBO.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
			sysUserInfoCreateServiceBO.setUpdateDate(currentEpochMilli);
			sysUserInfoCreateServiceBO.setUpdateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
			sysUserInfoCreateServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
			sysUserInfoService.create(sysUserInfoCreateServiceBO);
		}

		OauthToken oauthToken = doLogin(sysUser.getId(), sysUser.getUsername(), request);
		if (null == oauthToken) {
			log.error("------zch------小程序手机短信登录失败，请联系管理员进行处理 <{}>", param.toString());
			return ResponseEntity.badRequest().body("登录失败，请联系管理员进行处理");
		}

		log.info("------zch------小程序手机短信登录, param = <{}>", param.toString());
		return ResponseEntity.ok(oauthToken);
	}


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private OauthToken doLogin(Long userId, String username, HttpServletRequest request) {
		String userAgent = request.getHeader(GlobalConstant.HTTP_HEADER_USER_AGENT);
		String requestURI = request.getRequestURI();
		String requestIp = IPUtil.getIp(request);
		String locale = request.getLocale().getLanguage();

		String grantType = GlobalConstant.OAUTH_USER_ID_GRANT_TYPE;
		OauthTokenParam oauthTokenParam = new OauthTokenParam();
		oauthTokenParam.setGrantType(grantType);
		oauthTokenParam.setUserId(userId);
		oauthTokenParam.setClientId(GlobalConstantToJunit.CLIENT_ID);
		oauthTokenParam.setClientSecret(GlobalConstantToJunit.CLIENT_SECRET);

		try {
			// 生成 tkey token 返回给前端
			OauthTokenStrategyHandleBO oauthTokenStrategyHandleBO = new OauthTokenStrategyHandleBO();
			oauthTokenStrategyContext.checkParam(grantType, oauthTokenParam, oauthTokenStrategyHandleBO);

			OauthToken oauthToken = oauthTokenStrategyContext.generateOauthTokenInfo(grantType, oauthTokenParam, oauthTokenStrategyHandleBO);
			TkeyToken tkeyToken = getTkeyToken(oauthToken);

			Long maxTime = oauthClientProperties.getTokenMaxTimeToLiveInSeconds();
			if (UserAgentUtil.isMobile(request)) {
				// 移动端的token时效我们可以考虑设置为长一些
				maxTime = GlobalConstant.MOBILE_TOKEN_MAX_TIME_TO_LIVE_IN_SECONDS;
			}
			tokenRedisService.set(GlobalConstant.REDIS_MANAGEMENT_CLIENT_ACCESS_TOKEN_KEY_PREFIX + oauthToken.getAccessToken(), tkeyToken, maxTime);

			SysLoginLogCreateServiceBO serviceBO = new SysLoginLogCreateServiceBO();
			buildSysLoginLogCreateServiceBOToSuccess(serviceBO, oauthToken.getAccessToken(), requestURI, requestIp, userAgent, locale, oauthTokenParam.getClientId(), username);
			applicationContext.publishEvent(new LoginLogEvent(this, serviceBO));

			return oauthToken;
		} catch (Exception e) {
			ExceptionUtil.printStackTraceAsString(e);
			SysLoginLogCreateServiceBO serviceBO = new SysLoginLogCreateServiceBO();
			buildSysLoginLogCreateServiceBOToFailure(serviceBO, e.getMessage(), requestURI, requestIp, userAgent, locale, oauthTokenParam.getClientId(), username);
			applicationContext.publishEvent(new LoginLogEvent(this, serviceBO));
		}
		return null;
	}

	private TkeyToken getTkeyToken(OauthToken oauthToken) {
		TkeyToken tkeyToken = new TkeyToken();
		tkeyToken.setAccessToken(oauthToken.getAccessToken());
		tkeyToken.setRefreshToken(oauthToken.getRefreshToken());
		tkeyToken.setAttributes(tkeyService.getUserProfile(oauthToken.getAccessToken()));
		return tkeyToken;
	}

	private void buildSysLoginLogCreateServiceBOToFailure(SysLoginLogCreateServiceBO serviceBO, String errorMessage, String requestURI, String requestIp, String userAgent, String locale, String clientId, String username) {
		serviceBO.setMessage("短信登录失败：" + errorMessage);
		serviceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		serviceBO.setExceptionMsg(errorMessage);
		serviceBO.setClientId(clientId);
		serviceBO.setRequestUrl(requestURI);
		serviceBO.setUsername(username);
		serviceBO.setBoolLoginSuccessEnum(BooleanEnum.NO.getCode());
		serviceBO.setBoolNowOnlineEnum(BooleanEnum.NO.getCode());
		serviceBO.setIpAddress(requestIp);
		serviceBO.setUserAgent(userAgent);
		serviceBO.setBrowserLocale(locale);
		serviceBO.setLoginOriginEnum(LoginOriginEnum.WEB.getCode());
		serviceBO.setOfflineTypeEnum(OfflineTypeEnum.EXPIRE_LOGOUT.getCode());
		serviceBO.setLoginDate(DatetimeUtil.currentEpochMilli());
		serviceBO.setDeviceName(UserAgentUtil.getPlatform(userAgent));
		serviceBO.setOsName(UserAgentUtil.getOs(userAgent));
		serviceBO.setBrowserName(UserAgentUtil.getBrowser(userAgent));
	}

	private void buildSysLoginLogCreateServiceBOToSuccess(SysLoginLogCreateServiceBO serviceBO, String accessToken, String requestURI, String requestIp, String userAgent, String locale, String clientId, String username) {
		serviceBO.setMessage("短信登录成功");
		serviceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		serviceBO.setClientId(clientId);
		serviceBO.setToken(accessToken);
		serviceBO.setUsername(username);
		serviceBO.setBoolLoginSuccessEnum(BooleanEnum.YES.getCode());
		serviceBO.setBoolNowOnlineEnum(BooleanEnum.YES.getCode());
		serviceBO.setLoginOriginEnum(LoginOriginEnum.WEB.getCode());
		serviceBO.setOfflineTypeEnum(OfflineTypeEnum.EXPIRE_LOGOUT.getCode());
		serviceBO.setLoginDate(DatetimeUtil.currentEpochMilli());
		serviceBO.setRequestUrl(requestURI);
		serviceBO.setIpAddress(requestIp);
		serviceBO.setUserAgent(userAgent);
		serviceBO.setBrowserLocale(locale);
		serviceBO.setDeviceName(UserAgentUtil.getPlatform(userAgent));
		serviceBO.setOsName(UserAgentUtil.getOs(userAgent));
		serviceBO.setBrowserName(UserAgentUtil.getBrowser(userAgent));
	}


	// =====================================私有方法 end=====================================

}
