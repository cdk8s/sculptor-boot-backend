/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：DefaultParamAspect.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.aop.defaultparam;


import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.enums.UserTypeEnum;
import com.cdk8s.sculptor.exception.SystemException;
import com.cdk8s.sculptor.pojo.dto.param.bases.BaseCreateParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BaseDeleteParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BaseQueryParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BaseUpdateParam;
import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import com.cdk8s.sculptor.strategy.UserInfoContext;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.DatetimeUtil;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.response.biz.ResultObject;
import com.cdk8s.tkey.client.rest.pojo.dto.OauthUserAttribute;
import com.cdk8s.tkey.client.rest.pojo.dto.OauthUserProfile;
import com.github.pagehelper.PageInfo;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Order(1)
@Aspect
@Component
@Slf4j
public class DefaultParamAspect {

	@SneakyThrows
	@Around("execution(public * com.cdk8s.*.controller..*(..))")
	public Object doAround(ProceedingJoinPoint proceedingJoinPoint) {
		ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (null == servletRequestAttributes) {
			throw new SystemException("系统异常，无法获取到 servletRequestAttributes 对象");
		}
		// HttpServletRequest request = servletRequestAttributes.getRequest();
		// HttpServletResponse response = servletRequestAttributes.getResponse();
		// String param = request.getParameter("x-token");
		// String paramByHeader = request.getHeader("x-token");

		handleInputParam(proceedingJoinPoint.getArgs());//处理请求参数
		Object result = proceedingJoinPoint.proceed();
		handleOutputParam(result);//处理响应内容
		return result;
	}

	/**
	 * 处理请求参数
	 */
	private void handleInputParam(Object[] args) {
		for (Object arg : args) {
			long currentEpochMilli = DatetimeUtil.currentEpochMilli();
			// Long currentUserTenantId = UserInfoContext.getCurrentUserTenantId();

			// zchtodo 暂时没有多租户，所以全部设置为 1
			Long currentUserTenantId = 1L;

			Long currentUserId = UserInfoContext.getCurrentUserId();
			// if (UserInfoContext.isTopAdminUser()) {
			// 	// 如果是 admin 这个超级管理员，则可以看到所有数据，操作所有数据
			// 	currentUserTenantId = null;
			// }
			if (arg instanceof BaseQueryParam) {
				BaseQueryParam param = (BaseQueryParam) arg;
				param.setTenantId(currentUserTenantId);
				param.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
			} else if (arg instanceof BaseCreateParam) {
				// if (UserInfoContext.isTopAdminUser()) {
				// 	// 如果是 admin 这个超级管理员，则创建数据只能创建自己租户下的
				// 	currentUserTenantId = UserInfoContext.getCurrentUserTenantId();
				// }

				BaseCreateParam param = (BaseCreateParam) arg;
				param.setTenantId(currentUserTenantId);
				param.setCreateDate(currentEpochMilli);
				param.setCreateUserId(currentUserId);
				param.setUpdateDate(currentEpochMilli);
				param.setUpdateUserId(currentUserId);
				param.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
			} else if (arg instanceof BaseUpdateParam) {
				BaseUpdateParam param = (BaseUpdateParam) arg;
				param.setTenantId(currentUserTenantId);
				param.setUpdateDate(currentEpochMilli);
				param.setUpdateUserId(currentUserId);
			} else if (arg instanceof BaseDeleteParam) {
				BaseDeleteParam param = (BaseDeleteParam) arg;
				param.setTenantId(currentUserTenantId);
				param.setDeleteDate(currentEpochMilli);
				param.setDeleteUserId(currentUserId);
				param.setDeleteEnum(DeleteEnum.DELETED.getCode());
			}
		}
	}


	/**
	 * 处理返回对象
	 */
	private void handleOutputParam(Object obj) {
		// 如果是超级管理员角色用户则无需校验返回数据的租户 ID
		OauthUserProfile currentUser = UserInfoContext.getCurrentUser();
		Long tenantId = null;
		Integer userTypeEnumValue = null;
		if (null != currentUser) {
			OauthUserAttribute userAttribute = currentUser.getUserAttribute();
			if (null != userAttribute) {
				String userTypeString = userAttribute.getUserType();
				if (StringUtil.isNotBlank(userTypeString)) {
					userTypeEnumValue = Integer.valueOf(userTypeString);
				}
				if (null != userTypeEnumValue && userTypeEnumValue.equals(UserTypeEnum.ADMIN.getCode())) {
					// 后台管理员不需要做任何校验
					return;
				}

				String tenantIdString = userAttribute.getTenantId();
				if (StringUtil.isNotBlank(tenantIdString)) {
					tenantId = Long.valueOf(tenantIdString);
				}
			}
		}

		// 校验租户 ID
		if (null != tenantId && obj instanceof ResponseEntity) {
			ResponseEntity responseEntity = (ResponseEntity) obj;
			if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
				// 请求成功
				// HttpHeaders headers = responseEntity.getHeaders();
				Object body = responseEntity.getBody();
				if (body instanceof ResultObject) {
					ResultObject resultObject = (ResultObject) body;
					Object data = resultObject.getData();
					if (data instanceof PageInfo) {
						PageInfo pageInfo = (PageInfo) data;
						List infoList = pageInfo.getList();
						if (CollectionUtil.isNotEmpty(infoList)) {
							Object dto = infoList.get(0);
							if (dto instanceof BaseEntity) {
								BaseEntity baseEntity = (BaseEntity) dto;
								checkBaseEntity(baseEntity, tenantId);
							}
						}
					} else if (data instanceof BaseEntity) {
						BaseEntity baseEntity = (BaseEntity) data;
						checkBaseEntity(baseEntity, tenantId);
					}
				}
			}
		}
	}

	private void checkBaseEntity(BaseEntity baseEntity, Long tenantId) {
		Long tenantIdByResponse = baseEntity.getTenantId();
		// Long createUserId = baseEntity.getCreateUserId();
		if (null != tenantIdByResponse) {
			if (!tenantIdByResponse.equals(tenantId)) {
				throw new SystemException("请没有权限查看该企业数据，请联系管理员进行处理");
			}
		}
	}


}
