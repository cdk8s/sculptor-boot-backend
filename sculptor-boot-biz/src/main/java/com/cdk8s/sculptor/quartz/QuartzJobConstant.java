/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：QuartzJobConstant.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.quartz;

/**
 * 任务调度通用常量
 */
public interface QuartzJobConstant {

	String JOB_NAME_PREFIX = "TASK_";

	/**
	 * 执行目标 key
	 */
	String JOB_DATA_MAP_KEY = "TASK_SYS_JOB";

}
