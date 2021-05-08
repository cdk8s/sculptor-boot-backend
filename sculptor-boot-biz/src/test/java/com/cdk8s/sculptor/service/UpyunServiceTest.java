/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：UpyunServiceTest.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.Application;
import lombok.extern.slf4j.Slf4j;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@Ignore
@Slf4j
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class UpyunServiceTest {

	@Autowired
	private UpyunService upyunService;

	@Autowired
	private SysParamService sysParamService;


	@Test
	public void createDir() {
		upyunService.createDir("这是中文名");
	}

	@Test
	public void deleteDir() {
		upyunService.deleteDir("这是中文名");
	}

	@Test
	public void deleteFileList() {
		List<String> filePathList = new ArrayList<>();
		filePathList.add("/这是中文名/QQ20191202-141836.png");
		filePathList.add("/这是中文名/QQ20191202-142214.png");
		filePathList.add("/这是中文名/QQ20191202-1422142.png");
		upyunService.deleteFileList(filePathList);
	}

	@Test
	public void deleteFile() {
		String tempFileName = "byCreateDir.txt";
		String dirPath = "myNewDir";
		String upyunRootPath = (String) sysParamService.findOneValueByParamCode("path.upyunRootPath.string");
		String filePath = "/" + upyunRootPath + "/" + dirPath + "/" + tempFileName;
		upyunService.deleteFile(filePath);
	}
}
