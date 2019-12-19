package com.cdk8s.sculptor.constant;


public interface GlobalConstant {

	//=====================================LOG start=====================================

	// LOG 严重性能问题的时间阀值(毫秒)
	int SERIOUS_PERFORMANCE_PROBLEMS_TIME_THRESHOLD = 4000;

	// LOG 一般性能问题的时间阀值(毫秒)
	int GENERAL_PERFORMANCE_PROBLEMS_TIME_THRESHOLD = 3000;

	// LOG 修改优化的时间阀值(毫秒)
	int NEED_OPTIMIZE_TIME_THRESHOLD = 2000;

	//=====================================LOG end=====================================

	//=====================================biz start=====================================

	String HEADER_TOKEN_KEY = "x-token";
	String REDIS_MANAGEMENT_CLIENT_ACCESS_TOKEN_KEY_PREFIX = "OAUTH:MANAGEMENT_CLIENT:ACCESS_TOKEN:";

	// 注册后 7 天内的用户都算新用户（毫秒）
	Long NEW_USER_INTERVAL_TIME_MILLISECOND = 604800000L;

	Long DEFAULT_VALIDATE_CODE_EXPIRE_TIME_SECOND = 60L;

	String REDIS_VALIDATE_CODE_KEY_PREFIX = "VALIDATE_CODE:DEVICE_ID:";
	String REDIS_LOGIN_USERNAME_VALIDATE_CODE_KEY_PREFIX = "VALIDATE_CODE:LOGIN_USERNAME:";

	String TOP_ADMIN_USERNAME = "admin";
	Long TOP_ADMIN_USER_ID = 111111111111111111L;
	Long TOP_ADMIN_EMPLOYEE_ID = 111111111111111111L;

	String TOP_ADMIN_ROLE_CODE = "top_admin_role";
	Long TOP_ADMIN_ROLE_ID = 111111111111111111L;

	String TOP_ADMIN_DEPT_CODE = "top_admin_dept";
	Long TOP_ADMIN_DEPT_ID = 111111111111111111L;

	Long TOP_PERMISSION_PARENT_ID = 1L;

	Long CREATE_USER_DEFAULT_ROLE_ID = 333333333333333333L;
	Long CREATE_USER_DEFAULT_DEPT_ID = 333333333333333333L;


	//=====================================biz end=====================================
	//=====================================oauth start=====================================

	String DEFAULT_LOGIN_PAGE_CLIENT_INFO_KEY = "oauthClient";

	String DEFAULT_LOGIN_PAGE_PATH = "login";
	String DEFAULT_LOGIN_ERROR_KEY = "errorMsg";
	String DEFAULT_LOGOUT_PAGE_PATH = "logoutSuccess";
	String REDIRECT_URI_PREFIX = "redirect:";
	String HTTP_HEADER_USER_AGENT = "User-Agent";

	String OAUTH_TGC_PREFIX = "TGC-";
	String OAUTH_CODE_PREFIX = "OC-";
	String OAUTH_ACCESS_TOKEN_PREFIX = "AT-";
	String OAUTH_REFRESH_TOKEN_PREFIX = "RT-";

	String REDIS_TGC_KEY_PREFIX = "OAUTH:TGC:";
	String REDIS_CLIENT_ID_KEY_PREFIX = "OAUTH:CLIENT_ID:";
	String REDIS_OAUTH_CODE_PREFIX_KEY_PREFIX = "OAUTH:CODE:";
	String REDIS_OAUTH_ACCESS_TOKEN_KEY_PREFIX = "OAUTH:ACCESS_TOKEN:";
	String REDIS_OAUTH_REFRESH_TOKEN_KEY_PREFIX = "OAUTH:REFRESH_TOKEN:";
	String REDIS_OAUTH_USER_INFO_KEY_PREFIX = "OAUTH:USER_INFO:";

	String OAUTH_TOKEN_TYPE = "Bearer";
	String OAUTH_TOKEN_TYPE_UPPER_PREFIX = "Bearer ";
	String OAUTH_TOKEN_TYPE_LOWER_PREFIX = "bearer ";
	String BASIC_AUTH_UPPER_PREFIX = "Basic ";
	String BASIC_AUTH_LOWER_PREFIX = "basic ";

	String HTTP_HEADER_AUTHORIZATION = "Authorization";
	String OAUTH_SERVER_COOKIE_KEY = "tgc";
	String OAUTH_CODE_RESPONSE_TYPE = "code";
	String OAUTH_STATE_KEY = "state";
	String OAUTH_TOKEN_RESPONSE_TYPE = "token";
	String OAUTH_REFRESH_TOKEN_GRANT_TYPE = "refresh_token";
	String OAUTH_ACCESS_TOKEN_KEY = "access_token";
	String OAUTH_REFRESH_TOKEN_KEY = "refresh_token";
	String OAUTH_TOKEN_TYPE_KEY = "token_type";
	String OAUTH_EXPIRES_IN_KEY = "expires_in";
	String OAUTH_ACCESS_TOKEN_TYPE_HINT = "access_token";
	String OAUTH_CODE_GRANT_TYPE = "authorization_code";
	String OAUTH_CLIENT_GRANT_TYPE = "client_credentials";
	String OAUTH_TOKEN_GRANT_TYPE = "token";
	String OAUTH_PASSWORD_GRANT_TYPE = "password";

	String OAUTH_ERROR_URI_MSG = "See the full API docs at https://github.com/cdk8s";
	String OAUTH_ERROR_MSG = "invalid request";
	//=====================================oauth end=====================================

}
