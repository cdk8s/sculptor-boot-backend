/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：RelDeptUserControllerBase.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.controller.bases;

import com.cdk8s.sculptor.aop.eventlog.EventLog;
import com.cdk8s.sculptor.aop.eventlog.EventLogEnum;
import com.cdk8s.sculptor.mapstruct.IdAndIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.RelDeptUserMapStruct;
import com.cdk8s.sculptor.pojo.dto.param.bases.BatchDeleteRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdListRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.reldeptuser.*;
import com.cdk8s.sculptor.pojo.dto.response.reldeptuser.RelDeptUserResponseDTO;
import com.cdk8s.sculptor.pojo.entity.RelDeptUser;
import com.cdk8s.sculptor.service.RelDeptUserService;
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
public class RelDeptUserControllerBase {

	@Autowired
	private RelDeptUserService relDeptUserService;

	@Autowired
	private RelDeptUserMapStruct relDeptUserMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	// =====================================查询业务 start=====================================

	@RequestMapping(value = "/detail", method = RequestMethod.POST)
	public ResponseEntity<?> detail(@Valid @RequestBody IdRequestParam param) {
		RelDeptUserResponseDTO dto = relDeptUserMapStruct.toResponseDTO(relDeptUserService.findOneById(idAndIdListMapStruct.requestParamToServiceBO(param)));

		// if (null != dto) {

		// }

		return R.success(dto);
	}

	@RequestMapping(value = "/listByIdList", method = RequestMethod.POST)
	public ResponseEntity<?> listByIdList(@Valid @RequestBody IdListRequestParam param) {
		return R.success(relDeptUserMapStruct.toResponseDTOList(relDeptUserService.findListByIdList(idAndIdListMapStruct.requestParamToServiceBO(param))));
	}


	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseEntity<?> list(@Valid @RequestBody RelDeptUserPageQueryParam param) {
		List<RelDeptUser> result = relDeptUserService.findListByServiceBO(relDeptUserMapStruct.pageQueryParamToServiceBO(param));
		return R.success(relDeptUserMapStruct.toResponseDTOList(result));
	}


	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public ResponseEntity<?> page(@Valid @RequestBody RelDeptUserPageQueryParam param) {
		PageInfo result = relDeptUserService.findPageByServiceBO(relDeptUserMapStruct.pageQueryParamToServiceBO(param));
		List<RelDeptUserResponseDTO> list = relDeptUserMapStruct.toResponseDTOList(result.getList());

		// if (CollectionUtil.isNotEmpty(list)) {
		// for (RelDeptUserResponseDTO dto : list) {

		// }
		// }

		result.setList(list);
		return R.success(result);
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	@EventLog(message = "创建 relDeptUser 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody RelDeptUserCreateRequestParam param) {
		relDeptUserService.create(relDeptUserMapStruct.createRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量创建 relDeptUser 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestMapping(value = "/batchCreate", method = RequestMethod.POST)
	public ResponseEntity<?> batchCreate(@Valid @RequestBody RelDeptUserBatchCreateRequestParam param) {
		relDeptUserService.batchCreate(relDeptUserMapStruct.batchCreateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量创建 relDeptUser 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestMapping(value = "/batchCreateByDeptIdListToDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchCreateByDeptIdListToDelete(@Valid @RequestBody RelDeptUserBatchCreateRequestParam param) {
		relDeptUserService.batchCreateByDeptIdListToDelete(relDeptUserMapStruct.batchCreateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "批量创建 relDeptUser 对象", operateType = EventLogEnum.OPERATE_TYPE_CREATE)
	@RequestMapping(value = "/batchCreateByUserIdListToDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchCreateByUserIdListToDelete(@Valid @RequestBody RelDeptUserBatchCreateRequestParam param) {
		relDeptUserService.batchCreateByUserIdListToDelete(relDeptUserMapStruct.batchCreateRequestParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "更新 relDeptUser 对象", operateType = EventLogEnum.OPERATE_TYPE_UPDATE_INFO)
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody RelDeptUserUpdateRequestParam param) {
		relDeptUserService.update(relDeptUserMapStruct.updateRequestParamToServiceBO(param));
		return R.success();
	}


	@EventLog(message = "批量删除 relDeptUser 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestMapping(value = "/batchDelete", method = RequestMethod.POST)
	public ResponseEntity<?> batchDelete(@Valid @RequestBody BatchDeleteRequestParam param) {
		relDeptUserService.batchDelete(relDeptUserMapStruct.batchDeleteParamToServiceBO(param));
		return R.success();
	}

	@EventLog(message = "删除 relDeptUser 对象", operateType = EventLogEnum.OPERATE_TYPE_DELETE)
	@RequestMapping(value = "/deleteByDeptIdAndUserId", method = RequestMethod.POST)
	public ResponseEntity<?> deleteByDeptIdAndUserId(@Valid @RequestBody RelDeptUserDeptIdAndUserIdToDeleteRequestParam param) {
		relDeptUserService.deleteByDeptIdAndUserId(relDeptUserMapStruct.deleteByDeptIdAndUserIdParamToServiceBO(param));
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
