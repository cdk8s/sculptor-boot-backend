/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：QuartzTestTask.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 定时任务调度测试
 * cdk8stodo 预留功能
 */
@Component("myTestTask")
@Slf4j
public class QuartzTestTask {
	public void ryMultipleParams(String s, Boolean b, Long l, Double d, Integer i) {
		log.info("执行多参方法：字符串类型{}，布尔类型{}，长整型{}，浮点型{}，整形{}", s, b, l, d, i);
	}

	public void ryParams(String params) {
		log.info("执行有参方法：{}", params);
	}

	public void ryNoParams() {
		log.info("执行无参方法");
	}
}
