/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：TkeyWeixinQrController.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.login;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.constant.GlobalConstantToJunit;
import com.cdk8s.sculptor.enums.*;
import com.cdk8s.sculptor.eventlistener.event.LoginLogEvent;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.pojo.bo.handle.oauth.OauthTokenStrategyHandleBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysloginlog.SysLoginLogCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysuser.SysUserCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysuserinfo.SysUserInfoCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysuserinfo.SysUserInfoWeixinOpenidServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.oauth.OauthTokenParam;
import com.cdk8s.sculptor.pojo.dto.param.oauth.OauthWeixinQrParam;
import com.cdk8s.sculptor.pojo.dto.response.oauth.OauthToken;
import com.cdk8s.sculptor.pojo.entity.SysUser;
import com.cdk8s.sculptor.pojo.entity.SysUserInfo;
import com.cdk8s.sculptor.properties.OauthClientProperties;
import com.cdk8s.sculptor.service.SysUserInfoService;
import com.cdk8s.sculptor.service.SysUserService;
import com.cdk8s.sculptor.strategy.OauthTokenStrategyContext;
import com.cdk8s.sculptor.util.*;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import com.cdk8s.sculptor.util.ip.IPUtil;
import com.cdk8s.sculptor.util.okhttp.HttpService;
import com.cdk8s.sculptor.util.redis.StringRedisService;
import com.cdk8s.sculptor.weixin.pojo.response.WeixinQrLoginAccessTokenToUserInfoResponse;
import com.cdk8s.sculptor.weixin.pojo.response.WeixinQrLoginCodeToAccessTokenResponse;
import com.cdk8s.sculptor.weixin.properties.WeixinProperties;
import com.cdk8s.tkey.client.rest.pojo.dto.TkeyToken;
import com.cdk8s.tkey.client.rest.service.TkeyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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
@RequestMapping("/oauth")
public class TkeyWeixinQrController {

	@Autowired
	private SysUserService sysUserService;

	@Autowired
	private SysUserInfoService sysUserInfoService;

	@Autowired
	private OauthTokenStrategyContext oauthTokenStrategyContext;

	@Autowired
	private WeixinProperties weixinProperties;

	@Autowired
	private TkeyService tkeyService;

	@Autowired
	private StringRedisService<String, TkeyToken> tokenRedisService;

	@Autowired
	private ApplicationContext applicationContext;

	@Autowired
	private OauthClientProperties oauthClientProperties;

	@Autowired
	private StringRedisService<String, String> qrLoginRedisService;

	@Autowired
	private HttpService<WeixinQrLoginCodeToAccessTokenResponse> httpService;

	@Autowired
	private HttpService<WeixinQrLoginAccessTokenToUserInfoResponse> httpServiceByUserInfo;

	// =====================================查询业务 start=====================================


	@RequestMapping(value = "/weixinQr", method = RequestMethod.POST)
	public ResponseEntity<?> login(@Valid @RequestBody OauthWeixinQrParam param, HttpServletRequest request) {
		log.info("------zch------微信二维码登录, param = <{}>", param.toString());

		// 判断 state 是否正确，这个跳转地址应该是前端的一个路由，不然前端没法拿到 tkeyToken 值
		log.info("------zch------state <{}>", param.getState());
		log.info("------zch------code <{}>", param.getCode());
		String stateKey = GlobalConstant.WEIXIN_QR_LOGIN_STATE_KEY_PREFIX + param.getState();
		String result = qrLoginRedisService.get(stateKey);
		if (StringUtil.isBlank(result)) {
			throw new BusinessException("state 值不存在，请重试");
		}

		// 通过 code 换取 token
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type=authorization_code";
		url = StringUtil.format(url, weixinProperties.getWebsiteAppId(), weixinProperties.getWebsiteAppSecret(), param.getCode());
		WeixinQrLoginCodeToAccessTokenResponse weixinQrLoginCodeToAccessTokenResponse = httpService.get(url, WeixinQrLoginCodeToAccessTokenResponse.class, "微信 code 换取 token", "errcode");
		log.info("------zch------weixinQrLoginCodeToAccessTokenResponse <{}>", weixinQrLoginCodeToAccessTokenResponse.toString());

		// 查询是否已经绑定了 openid
		SysUserInfoWeixinOpenidServiceBO sysUserInfoWeixinOpenidServiceBO = new SysUserInfoWeixinOpenidServiceBO();
		sysUserInfoWeixinOpenidServiceBO.setWeixinOpenid(weixinQrLoginCodeToAccessTokenResponse.getOpenid());
		sysUserInfoWeixinOpenidServiceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		List<SysUserInfo> sysUserInfoList = sysUserInfoService.findListByWeixinOpenid(sysUserInfoWeixinOpenidServiceBO);

		Long userId;
		String username;
		if (CollectionUtil.isEmpty(sysUserInfoList)) {
			// 如果未绑定，则创建新用户，同时绑定微信信息
			userId = GenerateIdUtil.getId();

			// 如果未关联，通过 token 获取 info 信息，然后创建用户信息，然后再返回 tkeyToken
			url = "https://api.weixin.qq.com/sns/userinfo?access_token={0}&openid={1}";
			url = StringUtil.format(url, weixinQrLoginCodeToAccessTokenResponse.getAccess_token(), weixinQrLoginCodeToAccessTokenResponse.getOpenid());
			WeixinQrLoginAccessTokenToUserInfoResponse weixinQrLoginAccessTokenToUserInfoResponse = httpServiceByUserInfo.get(url, WeixinQrLoginAccessTokenToUserInfoResponse.class, "微信 token 换取 userinfo", "errcode");
			log.info("------zch------weixinQrLoginAccessTokenToUserInfoResponse <{}>", weixinQrLoginAccessTokenToUserInfoResponse.toString());

			username = weixinQrLoginAccessTokenToUserInfoResponse.getNickname();
			createUser(weixinQrLoginAccessTokenToUserInfoResponse, userId);
		} else {
			// 如果已经绑定，更新用户信息到 sys_user_info 和 sys_user 表中
			SysUserInfo sysUserInfo = sysUserInfoList.get(0);
			userId = sysUserInfo.getUserId();

			SysUser sysUser = sysUserService.findOneById(new IdServiceBO(userId));
			username = sysUser.getUsername();
		}

		OauthToken oauthToken = doLogin(userId, username, request);
		if (null == oauthToken) {
			log.error("------zch------微信二维码登录失败，请联系管理员进行处理 <{}>", param.toString());
			return ResponseEntity.badRequest().body("登录失败，请联系管理员进行处理");
		}

		log.info("------zch------微信二维码登录成功, param = <{}>", param.toString());
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

	private void createUser(WeixinQrLoginAccessTokenToUserInfoResponse weixinQrLoginAccessTokenToUserInfoResponse, Long userId) {
		SysUserCreateServiceBO sysUserCreateServiceBO = new SysUserCreateServiceBO();
		sysUserCreateServiceBO.setId(userId);
		sysUserCreateServiceBO.setUsername(weixinQrLoginAccessTokenToUserInfoResponse.getOpenid());
		sysUserCreateServiceBO.setNickname(weixinQrLoginAccessTokenToUserInfoResponse.getNickname());
		sysUserCreateServiceBO.setRealName(weixinQrLoginAccessTokenToUserInfoResponse.getNickname());
		sysUserCreateServiceBO.setUserPassword(RandomUtil.randomAlphabetic(6));
		sysUserCreateServiceBO.setAvatarUrl(weixinQrLoginAccessTokenToUserInfoResponse.getHeadimgurl());
		sysUserCreateServiceBO.setGenderEnum(weixinQrLoginAccessTokenToUserInfoResponse.getSex());
		sysUserCreateServiceBO.setUserTypeEnum(UserTypeEnum.USER.getCode());
		sysUserCreateServiceBO.setRegisterTypeEnum(RegisterTypeEnum.REGISTER.getCode());
		sysUserCreateServiceBO.setRegisterOriginEnum(RegisterOriginEnum.WEIXIN_MINI_PROGRAM.getCode());
		Long currentEpochMilli = DatetimeUtil.currentEpochMilli();
		sysUserCreateServiceBO.setCreateDate(currentEpochMilli);
		sysUserCreateServiceBO.setUpdateDate(currentEpochMilli);
		sysUserCreateServiceBO.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		sysUserCreateServiceBO.setUpdateUserId(GlobalConstant.TOP_ADMIN_USER_ID);

		// 创建用户扩展信息
		SysUserInfoCreateServiceBO sysUserInfoCreateServiceBO = new SysUserInfoCreateServiceBO();
		sysUserInfoCreateServiceBO.setUserId(userId);
		sysUserInfoCreateServiceBO.setWeixinOpenid(weixinQrLoginAccessTokenToUserInfoResponse.getOpenid());
		sysUserInfoCreateServiceBO.setWeixinUnionid(weixinQrLoginAccessTokenToUserInfoResponse.getUnionid());
		sysUserInfoCreateServiceBO.setWeixinUserinfo(JsonUtil.toJson(weixinQrLoginAccessTokenToUserInfoResponse));
		sysUserInfoCreateServiceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		sysUserInfoCreateServiceBO.setCreateDate(currentEpochMilli);
		sysUserInfoCreateServiceBO.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		sysUserInfoCreateServiceBO.setUpdateDate(currentEpochMilli);
		sysUserInfoCreateServiceBO.setUpdateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		sysUserService.createByWeixinLogin(sysUserCreateServiceBO, sysUserInfoCreateServiceBO);
	}

	private TkeyToken getTkeyToken(OauthToken oauthToken) {
		TkeyToken tkeyToken = new TkeyToken();
		tkeyToken.setAccessToken(oauthToken.getAccessToken());
		tkeyToken.setRefreshToken(oauthToken.getRefreshToken());
		tkeyToken.setAttributes(tkeyService.getUserProfile(oauthToken.getAccessToken()));
		return tkeyToken;
	}


	private void buildSysLoginLogCreateServiceBOToFailure(SysLoginLogCreateServiceBO serviceBO, String errorMessage, String requestURI, String requestIp, String userAgent, String locale, String clientId, String username) {
		serviceBO.setMessage("微信扫码登录失败：" + errorMessage);
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
		serviceBO.setMessage("微信扫码登录成功");
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
