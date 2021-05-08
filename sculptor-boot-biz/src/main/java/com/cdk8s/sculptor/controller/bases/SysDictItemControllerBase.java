/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysDictItemControllerBase.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.controller.bases;

import com.cdk8s.sculptor.aop.eventlog.EventLog;
import com.cdk8s.sculptor.aop.eventlog.EventLogEnum;
import com.cdk8s.sculptor.aop.permission.RequestPermission;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapstruct.IdAndIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.SysDictItemMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchUpdateStateRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.sysdictitem.*;
import com.cdk8s.sculptor.pojo.dto.response.sysdictitem.SysDictItemResponseDTO;
import com.cdk8s.sculptor.pojo.entity.SysDictItem;
import com.cdk8s.sculptor.service.SysDictItemService;
import com.cdk8s.sculptor.strategy.UserInfoContext;
import com.cdk8s.sculptor.util.response.biz.R;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.List;

@Slf4j
public class SysDictItemControllerBase {

	@Autowired
	private SysDictItemService sysDictItemService;

	@Autowired
	private SysDictItemMapStruct sysDictItemMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@RequestPermission("sys_dict_item")
	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ResponseEntity<?> detail(@Valid @RequestBody IdRequestParam param) {
		SysDictItemResponseDTO dto = sysDictItemMapStruct.toResponseDTO(sysDictItemService.findOneById(idAndIdListMapStruct.requestParamToServiceBO(param)));

		// if (null != dto) {

		// }

		return R.success(dto);
	}

	@RequestPermission("sys_dict_item")
	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(sysDictItemMapStruct.toResponseDTOList(sysDictItemService.findListByIdList(idAndIdListMapStruct.requestParamToServiceBO(param))));
	}


	@RequestPermission("sys_dict_item")
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@Valid @RequestBody SysDictItemPageQueryParam param) {
		List<SysDictItem> result = sysDictItemService.findListByServiceBO(sysDictItemMapStruct.pageQueryParamToServiceBO(param));
		return R.success(sysDictItemMapStruct.toResponseDTOList(result));
	}

	@RequestPermission("sys_dict_item")
	@RequestMapping(value = "/listByItemCode", method = RequestMethod.POST)
	public ResponseEntity<?> listByItemCode(@Valid @RequestBody SysDictItemItemCodeRequestParam param) {
		List<SysDictItem> result = sysDictItemService.findListByItemCode(sysDictItemMapStruct.itemCodeRequestParamToServiceBO(param));
		List<SysDictItemResponseDTO> dtoList = sysDictItemMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}

	@RequestPermission("sys_dict_item")
	@RequestMapping(value = "/listByDictCode", method = RequestMethod.POST)
	public ResponseEntity<?> listByDictCode(@Valid @RequestBody SysDictItemDictCodeRequestParam param) {
		List<SysDictItem> result = sysDictItemService.findListByDictCode(sysDictItemMapStruct.dictCodeRequestParamToServiceBO(param));
		List<SysDictItemResponseDTO> dtoList = sysDictItemMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}


	@RequestPermission("sys_dict_item")
	@RequestMapping(value = "/listByItemCodeList", method = RequestMethod.POST)
	public ResponseEntity<?> listByItemCodeList(@Valid @RequestBody SysDictItemItemCodeListRequestParam param) {
		List<SysDictItem> result = sysDictItemService.findListByItemCodeList(sysDictItemMapStruct.itemCodeListRequestParamToServiceBO(param));
		List<SysDictItemResponseDTO> dtoList = sysDictItemMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}

	@RequestPermission("sys_dict_item")
	@RequestMapping(value = "/listByDictCodeList", method = RequestMethod.POST)
	public ResponseEntity<?> listByDictCodeList(@Valid @RequestBody SysDictItemDictCodeListRequestParam param) {
		List<SysDictItem> result = sysDictItemService.findListByDictCodeList(sysDictItemMapStruct.dictCodeListRequestParamToServiceBO(param));
		List<SysDictItemResponseDTO> dtoList = sysDictItemMapStruct.toResponseDTOList(result);
		return R.success(dtoList);
	}


	@RequestPermission("sys_dict_item")
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody SysDictItemPageQueryParam param) {
		PageInfo result = sysDictItemService.findPageByServiceBO(sysDictItemMapStruct.pageQueryParamToServiceBO(param));
		List<SysDictItemResponseDTO> list = sysDictItemMapStruct.toResponseDTOList(result.getList());

		// if (CollectionUtil.isNotEmpty(list)) {
		// for (SysDictItemResponseDTO dto : list) {

		// }
		// }

		result.setList(list);
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestPermission("sys_dict_item")
	@RequestMapping(value = "/cacheEvict", method = RequestMethod.POST)
	public ResponseEntity<?> cacheEvict() {
		if (!UserInfoContext.isTopAdminUser()) {
			throw new BusinessException("只有顶级管理员才有权限使用该功能");
		}
		sysDictItemService.cacheEvict();
		return R.success();
	}

	@EventLog(message = "创建 sysDictItem 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestPermission("sys_dict_item_create")
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody SysDictItemCreateRequestParam param) {
		sysDictItemService.create(sysDictItemMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "更新 sysDictItem 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestPermission("sys_dict_item_update")
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody SysDictItemUpdateRequestParam param) {
		sysDictItemService.update(sysDictItemMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量更新 sysDictItem 状态", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_STATE)
	@RequestPermission("sys_dict_item_update")
	@RequestMapping(value = "/batchUpdateState", method = RequestMethod.POST)
	public ResponseEntity<?> batchUpdateState(@Valid @RequestBody BatchUpdateStateRequestParam param) {
		sysDictItemService.batchUpdateState(sysDictItemMapStruct.batchUpdateStateParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量删除 sysDictItem 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestPermission("sys_dict_item_delete")
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		sysDictItemService.batchDelete(sysDictItemMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
