package com.cdk8s.sculptor.aop.demomode;


import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.exception.SystemException;
import com.cdk8s.sculptor.service.SysParamService;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.UserInfoContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * 演示模式下是否可以编辑开关
 */
@Order(3)
@Aspect
@Component
@Slf4j
public class DemoModeAspect {

	@Autowired
	private SysParamService sysParamService;

	@Pointcut("execution(public * com.cdk8s.sculptor..controller..*.*(..))")
	public void demoModePointcut() {
	}

	@Before("demoModePointcut()")
	public void doBefore(JoinPoint joinPoint) {
		Long currentUserId = UserInfoContext.getCurrentUserId();
		if (GlobalConstant.TOP_ADMIN_USER_ID.equals(currentUserId)) {
			return;
		}

		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
		if (null == requestAttributes) {
			return;
		}

		HttpServletRequest request = ((ServletRequestAttributes) requestAttributes).getRequest();

		String requestURI = request.getRequestURI();

		if (StringUtil.containsAny(requestURI, "create", "update", "delete")) {
			String paramCode = "switch.demoModeEdit.boolean";
			Object paramValue = sysParamService.findOneValueByParamCode(paramCode);
			if (null == paramValue) {
				throw new SystemException("找不到演示模式编辑开关系统参数，请联系管理员进行处理");
			}

			Boolean flag = (Boolean) paramValue;
			if (!flag) {
				throw new SystemException("演示模式下无法编辑操作");
			}
		}


	}


}

