/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：CorsFilter.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.filter;

import com.cdk8s.sculptor.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class CorsFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;

		String origin = request.getHeader("Origin");
		if (StringUtil.isBlank(origin)) {
			origin = "*";
		}

		// 设置哪个源可以访问我
		response.addHeader("Access-Control-Allow-Origin", origin);
		// 允许携带哪个头访问我
		// response.addHeader("Access-Control-Allow-Headers", "*");


		response.addHeader("Access-Control-Allow-Headers", "multi-token, x-token, appKey, appSecret, epochMilli, shaString, Origin, X-Requested-With, Content-Type, Content-Disposition, X_Requested_With, Accept, X-HTTP-Method-Override, Cookie, AccessToken, PASSID, VerifyAuthToken, Anti-Content, ETag, Authorization");
		// 允许哪个方法访问我
		response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, PATCH");
		// 允许携带cookie
		response.addHeader("Access-Control-Allow-Credentials", "true");
		// 预检的存活时间
		response.addHeader("Access-Control-Max-Age", "3600");
		// 允许返回的头
		response.addHeader("Access-Control-Expose-Headers", "*");

		chain.doFilter(req, res);
	}

	@Override
	public void init(FilterConfig filterConfig) {
	}

	@Override
	public void destroy() {
	}
}
