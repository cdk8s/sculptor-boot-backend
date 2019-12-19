package com.cdk8s.sculptor.aop.permission;


import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.exception.ForbiddenException;
import com.cdk8s.sculptor.service.SysPermissionService;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.DatetimeUtil;
import com.cdk8s.sculptor.util.UserInfoContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Order(100)
@Aspect
@Component
@Slf4j
public class RequestPermissionAspect {

	@Autowired
	private SysPermissionService sysPermissionService;

	@SneakyThrows
	@Around("@annotation(requestPermission)")
	public Object around(ProceedingJoinPoint point, RequestPermission requestPermission) {
		Long currentUserId = UserInfoContext.getCurrentUserId();
		if (GlobalConstant.TOP_ADMIN_USER_ID.equals(currentUserId)) {
			return point.proceed();
		}

		String strClassName = point.getTarget().getClass().getName();
		String strMethodName = point.getSignature().getName();
		log.debug("RequestPermissionAspect 类名:<{}>, 方法:<{}>", strClassName, strMethodName);
		long startTime = DatetimeUtil.currentEpochMilli();

		String[] permissionCode = requestPermission.value();
		int requestPermissionType = requestPermission.requestPermissionType();
		if (permissionCode.length > 0) {
			hasPermissionVerify(currentUserId, permissionCode, requestPermissionType);
		}

		Object result = point.proceed();
		log.debug("RequestPermissionAspect 耗时:<{}ms>", (DatetimeUtil.currentEpochMilli() - startTime));
		return result;
	}

	private void hasPermissionVerify(Long currentUserId, String[] permissionCode, int requestPermissionType) {
		List<String> permissionCodeList = sysPermissionService.findPermissionCodeListByUserId(currentUserId);
		if (CollectionUtil.isEmpty(permissionCodeList)) {
			throw new ForbiddenException("您没有该操作的权限，请联系管理员处理");
		}

		List<String> list = CollectionUtil.toList(permissionCode);
		List<String> intersection = CollectionUtil.intersection(list, permissionCodeList);

		if (requestPermissionType == RequestPermissionEnum.OR_RELATION.getCode()) {
			if (intersection.size() < list.size()) {
				throw new ForbiddenException("您没有该操作的权限，请联系管理员处理");
			}
		}
		if (intersection.size() != list.size()) {
			throw new ForbiddenException("您没有该操作的权限，请联系管理员处理");
		}
	}


}
