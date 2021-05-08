/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysCommonOpenController.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.controller;

import com.cdk8s.sculptor.enums.util.EnumInfoItemDTO;
import com.cdk8s.sculptor.enums.util.EnumUtil;
import com.cdk8s.sculptor.util.response.biz.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


@Slf4j
@RestController
@RequestMapping("/api/open/sysCommon")
public class SysCommonOpenController {

	private static Map<String, List<EnumInfoItemDTO>> enumList = EnumUtil.getEnumList();

	// =====================================查询业务 start=====================================

	@RequestMapping(value = "/enumList", method = RequestMethod.GET)
	public ResponseEntity<?> enumList() {
		return R.success(enumList);
	}


	// =====================================私有方法 end=====================================

}
