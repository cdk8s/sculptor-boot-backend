/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：FrontendMockController.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.controller;

import com.cdk8s.sculptor.util.response.biz.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * cdk8stodo 预留功能
 */
@Slf4j
@RestController
@RequestMapping("/api/open/mock")
public class FrontendMockController {


	// =====================================查询业务 start=====================================

	@RequestMapping(value = "/data", method = RequestMethod.POST)
	public ResponseEntity<?> data() {

		Map<String, Object> map_1 = new HashMap<>();
		map_1.put("label", "总的专家数量1");
		map_1.put("value", 11);
		map_1.put("gotoUrl", "/index");
		Map<String, Object> map_2 = new HashMap<>();
		map_2.put("label", "总的专家数量2");
		map_2.put("value", 11);
		map_2.put("gotoUrl", "/index");
		Map<String, Object> map_3 = new HashMap<>();
		map_3.put("label", "总的专家数量3");
		map_3.put("value", 11);
		map_3.put("gotoUrl", "/index");

		List<Object> list = new ArrayList<>();
		list.add(map_1);
		list.add(map_2);
		list.add(map_3);

		return R.success(list);
	}

	@RequestMapping(value = "/trendData", method = RequestMethod.POST)
	public ResponseEntity<?> trendData() {

		Map<String, Object> map_1 = new HashMap<>();
		map_1.put("year", "2019-11");
		map_1.put("value", 2);
		Map<String, Object> map_2 = new HashMap<>();
		map_2.put("year", "2020-03");
		map_2.put("value", 3);
		Map<String, Object> map_3 = new HashMap<>();
		map_3.put("year", "2020-04");
		map_3.put("value", 6);

		List<Object> list = new ArrayList<>();
		list.add(map_1);
		list.add(map_2);
		list.add(map_3);

		return R.success(list);
	}

	@RequestMapping(value = "/topData", method = RequestMethod.POST)
	public ResponseEntity<?> topData() {

		Map<String, Object> map_1 = new HashMap<>();
		map_1.put("sold", 12);
		map_1.put("genre", "张三");
		Map<String, Object> map_2 = new HashMap<>();
		map_2.put("sold", 7);
		map_2.put("genre", "李四");
		Map<String, Object> map_3 = new HashMap<>();
		map_3.put("sold", 1);
		map_3.put("genre", "王五");

		List<Object> list = new ArrayList<>();
		list.add(map_1);
		list.add(map_2);
		list.add(map_3);

		return R.success(list);
	}


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}
