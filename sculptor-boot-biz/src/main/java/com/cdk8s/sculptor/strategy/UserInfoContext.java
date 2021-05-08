/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：UserInfoContext.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.strategy;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.UserTypeEnum;
import com.cdk8s.sculptor.exception.SystemException;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdServiceBO;
import com.cdk8s.sculptor.service.SysUserService;
import com.cdk8s.sculptor.util.SpringUtil;
import com.cdk8s.tkey.client.rest.pojo.dto.OauthUserProfile;

import java.util.Map;

public class UserInfoContext {
	private static ThreadLocal<OauthUserProfile> userInfo = new ThreadLocal<>();

	public UserInfoContext() {
	}

	public static void setCurrentUser(OauthUserProfile user) {
		userInfo.set(user);
	}

	public static OauthUserProfile getCurrentUser() {
		return userInfo.get();
	}

	public static Long getCurrentUserId() {
		if (null == getCurrentUser()) {
			return null;
		}
		return Long.valueOf(getCurrentUser().getUserId());
	}

	public static Long getCurrentUserTenantId() {
		if (null == getCurrentUser()) {
			return null;
		}

		Map<String, Object> userAttributeExt = getCurrentUser().getUserAttribute().getUserAttributeExt();
		Object tenantIdObject = userAttributeExt.get(GlobalConstant.TENANT_ID_MAP_KEY);
		if (null == tenantIdObject) {
			throw new SystemException("获取不到 tenantId");
		}
		if (tenantIdObject instanceof Integer) {
			return ((Integer) tenantIdObject).longValue();
		}
		return (Long) tenantIdObject;
	}

	public static Integer getCurrentUserTypeEnum() {
		if (null == getCurrentUser()) {
			return null;
		}

		Map<String, Object> userAttributeExt = getCurrentUser().getUserAttribute().getUserAttributeExt();
		Object userTypeEnumObject = userAttributeExt.get(GlobalConstant.USER_TYPE_ENUM_MAP_KEY);
		if (null == userTypeEnumObject) {
			throw new SystemException("获取不到 userTypeEnum");
		}
		return (Integer) userTypeEnumObject;
	}

	public static String getCurrentUsername() {
		if (null == getCurrentUser()) {
			return null;
		}
		return getCurrentUser().getUsername();
	}

	public static Boolean isTopAdminUser() {
		return null != getCurrentUserId() && getCurrentUserId().equals(GlobalConstant.TOP_ADMIN_USER_ID);
	}

	public static Boolean isAdminUser() {
		Integer currentUserTypeEnum = getCurrentUserTypeEnum();
		if (null == currentUserTypeEnum) {
			throw new SystemException("获取不到 userTypeEnum");
		}

		if (currentUserTypeEnum.equals(UserTypeEnum.ADMIN.getCode())) {
			return true;
		}
		return false;
	}

	public static Boolean isTenantUser() {
		Integer currentUserTypeEnum = getCurrentUserTypeEnum();
		if (null == currentUserTypeEnum) {
			throw new SystemException("获取不到 userTypeEnum");
		}

		if (currentUserTypeEnum.equals(UserTypeEnum.TENANT_ADMIN.getCode()) || currentUserTypeEnum.equals(UserTypeEnum.TENANT_SUB_ACCOUNT.getCode())) {
			return true;
		}
		return false;
	}

	public static String getUsernameByUserId(Long userId) {
		IdServiceBO idServiceBO = new IdServiceBO();
		idServiceBO.setId(userId);
		return SpringUtil.getBean(SysUserService.class).findUsernameById(idServiceBO);
	}

	public static String getRealNameByUserId(Long userId) {
		IdServiceBO idServiceBO = new IdServiceBO();
		idServiceBO.setId(userId);
		return SpringUtil.getBean(SysUserService.class).findRealNameById(idServiceBO);
	}

	public static void remove() {
		userInfo.remove();
	}
}
