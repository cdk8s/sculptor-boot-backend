package com.cdk8s.sculptor.util;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.service.SysUserService;
import com.cdk8s.tkey.client.rest.pojo.dto.OauthUserProfile;

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
		return Long.valueOf(userInfo.get().getUserId());
	}

	public static String getCurrentUsername() {
		if (null == getCurrentUser()) {
			return null;
		}
		return userInfo.get().getUsername();
	}

	public static Boolean isTopAdminUser() {
		return null != getCurrentUserId() && getCurrentUserId().equals(GlobalConstant.TOP_ADMIN_USER_ID);
	}

	public static String getUsernameByUserId(Long userId) {
		return SpringUtil.getBean(SysUserService.class).findUsernameById(userId);
	}

	public static void remove() {
		userInfo.remove();
	}
}
