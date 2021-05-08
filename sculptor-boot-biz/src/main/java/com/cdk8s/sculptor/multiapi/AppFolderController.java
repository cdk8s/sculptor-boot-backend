/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：AppFolderController.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.multiapi;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.BooleanEnum;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.enums.StateEnum;
import com.cdk8s.sculptor.mapstruct.IdAndIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.ParentIdAndParentIdListMapStruct;
import com.cdk8s.sculptor.mapstruct.SysFolderInfoMapStruct;
import com.cdk8s.sculptor.multiapi.pojo.param.AppSysFolderInfoCreateRequestParam;
import com.cdk8s.sculptor.multiapi.pojo.param.AppSysFolderInfoUpdateRequestParam;
import com.cdk8s.sculptor.pojo.bo.service.bases.ParentIdServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysfolderinfo.SysFolderInfoCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysfolderinfo.SysFolderInfoUpdateServiceBO;
import com.cdk8s.sculptor.pojo.dto.param.bases.IdRequestParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.ParentIdRequestParam;
import com.cdk8s.sculptor.pojo.dto.response.sysfolderinfo.SysFolderInfoResponseDTO;
import com.cdk8s.sculptor.service.SysFolderInfoService;
import com.cdk8s.sculptor.util.response.biz.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/multiapi")
public class AppFolderController {

	@Autowired
	private SysFolderInfoService sysFolderInfoService;

	@Autowired
	private SysFolderInfoMapStruct sysFolderInfoMapStruct;

	@Autowired
	private IdAndIdListMapStruct idAndIdListMapStruct;

	@Autowired
	private ParentIdAndParentIdListMapStruct parentIdAndParentIdListMapStruct;

	// =====================================查询业务 start=====================================

	@RequestMapping(value = "/open/t294t_sys_folder_info", method = RequestMethod.POST)
	public ResponseEntity<?> listTreeByParentId(@Valid @RequestBody ParentIdRequestParam param) {
		ParentIdServiceBO serviceBO = parentIdAndParentIdListMapStruct.requestParamToServiceBO(param);
		return R.success(sysFolderInfoMapStruct.toResponseDTOList(sysFolderInfoService.findTreeListByParentId(serviceBO)));
	}

	@RequestMapping(value = "/open/t295v_sys_folder_info", method = RequestMethod.POST)
	public ResponseEntity<?> detail(@Valid @RequestBody IdRequestParam param) {
		SysFolderInfoResponseDTO dto = sysFolderInfoMapStruct.toResponseDTO(sysFolderInfoService.findOneById(idAndIdListMapStruct.requestParamToServiceBO(param)));

		// if (null != dto) {

		// }

		return R.success(dto);
	}


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@RequestMapping(value = "/open/t296c_sys_folder_info", method = RequestMethod.POST)
	public ResponseEntity<?> create(@Valid @RequestBody AppSysFolderInfoCreateRequestParam param) {
		SysFolderInfoCreateServiceBO sysFolderInfoCreateServiceBO = new SysFolderInfoCreateServiceBO();
		sysFolderInfoCreateServiceBO.setParentId(param.getParentId());
		sysFolderInfoCreateServiceBO.setFolderName(param.getFolderName());
		sysFolderInfoCreateServiceBO.setRanking(100);
		sysFolderInfoCreateServiceBO.setBoolTopEnum(BooleanEnum.NO.getCode());
		sysFolderInfoCreateServiceBO.setStateEnum(StateEnum.ENABLE.getCode());
		sysFolderInfoCreateServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		sysFolderInfoCreateServiceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		sysFolderInfoService.create(sysFolderInfoCreateServiceBO);
		return R.success();
	}

	@RequestMapping(value = "/open/t297e_sys_folder_info", method = RequestMethod.POST)
	public ResponseEntity<?> update(@Valid @RequestBody AppSysFolderInfoUpdateRequestParam param) {
		SysFolderInfoUpdateServiceBO sysFolderInfoUpdateServiceBO = new SysFolderInfoUpdateServiceBO();
		sysFolderInfoUpdateServiceBO.setId(param.getId());
		sysFolderInfoUpdateServiceBO.setParentId(param.getParentId());
		sysFolderInfoUpdateServiceBO.setFolderName(param.getFolderName());
		sysFolderInfoService.update(sysFolderInfoUpdateServiceBO);
		return R.success();
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================


	// =====================================私有方法 end=====================================

}
