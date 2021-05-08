/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysPermissionController.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.controller;

import com.cdk8s.sculptor.aop.eventlog.EventLog;
import com.cdk8s.sculptor.aop.eventlog.EventLogEnum;
import com.cdk8s.sculptor.aop.permission.RequestPermission;
import com.cdk8s.sculptor.controller.bases.SysPermissionControllerBase;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapstruct.ParentIdAndParentIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.SysPermissionMapStruct;
import com.cdk8s.sculptor.pojo.bo.service.bases.ParentIdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.UserIdServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.ParentIdRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.syspermission.SysPermissionResponseDTOToVue3;
import com.cdk8s.sculptor.pojo.entity.SysPermission;
import com.cdk8s.sculptor.service.SysPermissionService;
import com.cdk8s.sculptor.strategy.UserInfoContext;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.response.biz.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/sysPermission")
public class SysPermissionController extends SysPermissionControllerBase {

	@Autowired
	private SysPermissionService sysPermissionService;

	@Autowired
	private SysPermissionMapStruct sysPermissionMapStruct;

	@Autowired
	private ParentIdAndParentIdListMapStruct parentIdAndParentIdListMapStruct;

	// =====================================查询业务 start=====================================

	@RequestPermission("sys_permission")
	@RequestMapping(value = "/listTreeByParentIdNotButton", method = RequestMethod.POST)
	public ResponseEntity<?> listTreeByParentIdNotButton(@Valid @RequestBody ParentIdRequestParam param) {
		ParentIdServiceBO serviceBO = parentIdAndParentIdListMapStruct.requestParamToServiceBO(param);
		return R.success(sysPermissionMapStruct.toResponseDTOList(sysPermissionService.findTreeListByParentIdNotButton(serviceBO)));
	}


	@RequestMapping(value = "/listTreeByCurrentUserPermission", method = RequestMethod.POST)
	public ResponseEntity<?> listTreeByCurrentUserPermission() {
		UserIdServiceBO serviceBO = new UserIdServiceBO();
		serviceBO.setUserId(UserInfoContext.getCurrentUserId());
		serviceBO.setTenantId(UserInfoContext.getCurrentUserTenantId());
		serviceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		return R.success(sysPermissionMapStruct.toResponseDTOList(sysPermissionService.findTreeListByUserIdNotButton(serviceBO)));
	}

	@RequestMapping(value = "/listTreeByCurrentUserPermissionByVue3", method = RequestMethod.POST)
	public ResponseEntity<?> listTreeByCurrentUserPermissionByVue3() {
		UserIdServiceBO serviceBO = new UserIdServiceBO();
		serviceBO.setUserId(UserInfoContext.getCurrentUserId());
		serviceBO.setTenantId(UserInfoContext.getCurrentUserTenantId());
		serviceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		List<SysPermission> initList = sysPermissionService.findListByUserIdNotButton(serviceBO);

		if (CollectionUtil.isEmpty(initList)) {
			return R.success();
		}

		//把所有结果转换成VO
		List<SysPermissionResponseDTOToVue3> initVOList = new ArrayList<>();
		for (SysPermission sysPermission : initList) {
			initVOList.add(buildResponseDTOToVue3(sysPermission));
		}

		List<SysPermissionResponseDTOToVue3> responseDTOToVue3List = buildTreeList(initVOList);
		return R.success(responseDTOToVue3List);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@Override
	@EventLog(message = "批量删除 sysPermission 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_permission_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysPermissionService.batchDelete(sysPermissionMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	private SysPermissionResponseDTOToVue3 buildResponseDTOToVue3(SysPermission entity) {
		SysPermissionResponseDTOToVue3 responseDTOToVue3 = new SysPermissionResponseDTOToVue3();
		responseDTOToVue3.setId(entity.getId());
		responseDTOToVue3.setMenuId(entity.getId());
		responseDTOToVue3.setParentId(entity.getParentId());
		responseDTOToVue3.setTitle(entity.getPermissionName());
		responseDTOToVue3.setIcon(entity.getIconClass());
		responseDTOToVue3.setPath(entity.getPermissionUrl());
		responseDTOToVue3.setComponent(entity.getPermissionUrl());
		responseDTOToVue3.setTarget("_self");// zchtodo 是否新标签打开这个需要和前端配合改为动态判断，可选值："_blank", "_self"
		responseDTOToVue3.setListPath(null);
		Integer visibleEnum = entity.getVisibleEnum();
		// 不同系统对应枚举值不一样，这里进行转换
		if (visibleEnum.equals(1)) {
			// 要显示
			responseDTOToVue3.setHide(0);
		} else {
			// 要隐藏
			responseDTOToVue3.setHide(1);
		}
		return responseDTOToVue3;
	}

	/**
	 * 第二种递归方式：构建树形结构（其他方法调用该方法）
	 *
	 * @return
	 */
	public List<SysPermissionResponseDTOToVue3> buildTreeList(List<SysPermissionResponseDTOToVue3> initVOList) {
		List<SysPermissionResponseDTOToVue3> voResultList = new ArrayList<>();
		List<SysPermissionResponseDTOToVue3> rootNodes = getAllRootNodes(initVOList);
		for (SysPermissionResponseDTOToVue3 rootNode : rootNodes) {
			buildChildNodes(initVOList, rootNode);
			voResultList.add(rootNode);
		}
		return voResultList;
	}


	/**
	 * 递归子节点
	 *
	 * @param rootNode
	 */

	public void buildChildNodes(List<SysPermissionResponseDTOToVue3> initVOList, SysPermissionResponseDTOToVue3 rootNode) {
		List<SysPermissionResponseDTOToVue3> nodeChildList = getChildNodes(initVOList, rootNode);
		if (!nodeChildList.isEmpty()) {
			for (SysPermissionResponseDTOToVue3 childObject : nodeChildList) {
				buildChildNodes(initVOList, childObject);
			}
			rootNode.setChildren(nodeChildList);
		}
	}


	/**
	 * 获取父节点下所有的子节点
	 */

	public List<SysPermissionResponseDTOToVue3> getChildNodes(List<SysPermissionResponseDTOToVue3> initList, SysPermissionResponseDTOToVue3 parentObject) {
		List<SysPermissionResponseDTOToVue3> childNodeList = new ArrayList<>();
		for (SysPermissionResponseDTOToVue3 tempObject : initList) {
			if (parentObject.getId().equals(tempObject.getParentId())) {
				childNodeList.add(tempObject);
			}
		}
		return childNodeList;
	}

	/**
	 * 获取集合中所有最顶层的根节点
	 */

	public List<SysPermissionResponseDTOToVue3> getAllRootNodes(List<SysPermissionResponseDTOToVue3> initVOList) {
		List<SysPermissionResponseDTOToVue3> rootNodes = new ArrayList<>();
		//如果要提高效率的话，我们可以在明知道最顶层节点的父ID是0的情况下进行这样的处理：
		for (SysPermissionResponseDTOToVue3 tempObject : initVOList) {
			if (tempObject.getParentId().equals(1L)) {
				rootNodes.add(tempObject);
			}
		}

		return rootNodes;

	}


	/**
	 * 判断是否为根节点
	 */
	public boolean isRootNode(List<SysPermissionResponseDTOToVue3> initList, SysPermissionResponseDTOToVue3 node) {
		boolean isRootNodeFlag = true;
		for (SysPermissionResponseDTOToVue3 temp : initList) {
			if (node.getParentId().equals(temp.getId())) {
				isRootNodeFlag = false;
				break;
			}
		}
		return isRootNodeFlag;
	}

	// =====================================私有方法 end=====================================

}
