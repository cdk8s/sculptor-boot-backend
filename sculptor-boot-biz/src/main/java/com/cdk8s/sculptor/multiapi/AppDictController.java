/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：AppDictController.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.multiapi;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.multiapi.pojo.param.AppDictCodeRequestParam;
import com.cdk8s.sculptor.multiapi.pojo.response.AppDictItemResponseDTO;
import com.cdk8s.sculptor.pojo.bo.service.sysdictitem.SysDictItemDictCodeServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysDictItem;
import com.cdk8s.sculptor.service.SysDictItemService;
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
@RequestMapping("/multiapi")
public class AppDictController {

	@Autowired
	private SysDictItemService sysDictItemService;

	// =====================================查询业务 start=====================================

	@RequestMapping(value = "/open/dictItemList", method = RequestMethod.POST)
	public ResponseEntity<?> index(@Valid @RequestBody AppDictCodeRequestParam param) {
		SysDictItemDictCodeServiceBO serviceBO = new SysDictItemDictCodeServiceBO();
		serviceBO.setDictCode(param.getDictCode());
		serviceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		serviceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		List<SysDictItem> listByDictCode = sysDictItemService.findListByDictCode(serviceBO);
		if (CollectionUtil.isEmpty(listByDictCode)) {
			return R.success();
		}

		List<AppDictItemResponseDTO> dtoList = new ArrayList<>();
		for (SysDictItem entity : listByDictCode) {
			AppDictItemResponseDTO dto = new AppDictItemResponseDTO();
			dto.setItemName(entity.getItemName());
			dto.setItemCode(entity.getItemCode());
			dto.setItemValue(entity.getItemValue());
			dtoList.add(dto);
		}

		return R.success(dtoList);
	}


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================


	// =====================================私有方法 end=====================================
}
