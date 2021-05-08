/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：OauthLoginApiService.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service.oauth;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.enums.ResponseProduceTypeEnum;
import com.cdk8s.sculptor.enums.StateEnum;
import com.cdk8s.sculptor.exception.OauthApiException;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysuser.SysUserUsernameServiceBO;
import com.cdk8s.sculptor.pojo.dto.response.oauth.OauthUserAttribute;
import com.cdk8s.sculptor.pojo.entity.SysUser;
import com.cdk8s.sculptor.service.SysUserService;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.code.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
@Slf4j
public class OauthLoginApiService {

	@Autowired
	private SysUserService sysUserService;

	//=====================================业务处理 start=====================================

	public OauthUserAttribute requestLoginApi(String username, String passsword) {
		username = StringUtil.trim(username);
		SysUserUsernameServiceBO sysUserUsernameServiceBO = new SysUserUsernameServiceBO();
		sysUserUsernameServiceBO.setUsername(username);
		sysUserUsernameServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		SysUser sysUser = sysUserService.findOneByUsername(sysUserUsernameServiceBO);
		if (null == sysUser) {
			throw new OauthApiException("用户不存在", ResponseProduceTypeEnum.HTML, GlobalConstant.DEFAULT_LOGIN_PAGE_PATH);
		}

		if (sysUser.getStateEnum().equals(StateEnum.DISABLE.getCode())) {
			throw new OauthApiException("该用户已被停用", ResponseProduceTypeEnum.HTML, GlobalConstant.DEFAULT_LOGIN_PAGE_PATH);
		}

		String passwordByMD5 = Md5Util.md5ByGuava(passsword + sysUser.getPasswordSalt());

		if (StringUtil.notEquals(sysUser.getUserPassword(), passwordByMD5)) {
			throw new OauthApiException("用户名或密码错误", ResponseProduceTypeEnum.HTML, GlobalConstant.DEFAULT_LOGIN_PAGE_PATH);
		}

		OauthUserAttribute oauthUserAttribute = new OauthUserAttribute();
		oauthUserAttribute.setUserId(String.valueOf(sysUser.getId()));
		oauthUserAttribute.setUsername(sysUser.getUsername());
		oauthUserAttribute.setRealName(sysUser.getRealName());
		oauthUserAttribute.setNickname(sysUser.getNickname());
		oauthUserAttribute.setAvatarUrl(sysUser.getAvatarUrl());
		oauthUserAttribute.setEmail(sysUser.getUserEmail());
		oauthUserAttribute.setMobilePhone(sysUser.getMobilePhone());
		oauthUserAttribute.setUserType(String.valueOf(sysUser.getUserTypeEnum()));
		oauthUserAttribute.setTenantId(String.valueOf(sysUser.getTenantId()));

		Map<String, Object> userAttributeExt = new HashMap<>();
		userAttributeExt.put(GlobalConstant.USER_TYPE_ENUM_MAP_KEY, sysUser.getUserTypeEnum());
		userAttributeExt.put(GlobalConstant.TENANT_ID_MAP_KEY, sysUser.getTenantId());

		oauthUserAttribute.setUserAttributeExt(userAttributeExt);
		return oauthUserAttribute;
	}

	public OauthUserAttribute requestLoginByUserId(Long userId) {
		IdServiceBO idServiceBO = new IdServiceBO();
		idServiceBO.setId(userId);
		idServiceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		idServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		SysUser sysUser = sysUserService.findOneById(idServiceBO);
		if (null == sysUser) {
			throw new OauthApiException("用户不存在", ResponseProduceTypeEnum.HTML, GlobalConstant.DEFAULT_LOGIN_PAGE_PATH);
		}

		if (sysUser.getStateEnum().equals(StateEnum.DISABLE.getCode())) {
			throw new OauthApiException("该用户已被停用", ResponseProduceTypeEnum.HTML, GlobalConstant.DEFAULT_LOGIN_PAGE_PATH);
		}

		OauthUserAttribute oauthUserAttribute = new OauthUserAttribute();
		oauthUserAttribute.setUserId(String.valueOf(sysUser.getId()));
		oauthUserAttribute.setUsername(sysUser.getUsername());
		oauthUserAttribute.setRealName(sysUser.getRealName());
		oauthUserAttribute.setNickname(sysUser.getNickname());
		oauthUserAttribute.setAvatarUrl(sysUser.getAvatarUrl());
		oauthUserAttribute.setEmail(sysUser.getUserEmail());
		oauthUserAttribute.setMobilePhone(sysUser.getMobilePhone());
		oauthUserAttribute.setUserType(String.valueOf(sysUser.getUserTypeEnum()));
		oauthUserAttribute.setTenantId(String.valueOf(sysUser.getTenantId()));

		Map<String, Object> userAttributeExt = new HashMap<>();
		userAttributeExt.put(GlobalConstant.USER_TYPE_ENUM_MAP_KEY, sysUser.getUserTypeEnum());
		userAttributeExt.put(GlobalConstant.TENANT_ID_MAP_KEY, sysUser.getTenantId());

		oauthUserAttribute.setUserAttributeExt(userAttributeExt);
		return oauthUserAttribute;
	}

	//=====================================业务处理  end=====================================

	//=====================================私有方法 start=====================================


	//=====================================私有方法  end=====================================

}
