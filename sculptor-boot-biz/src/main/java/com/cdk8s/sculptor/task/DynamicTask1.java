/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：DynamicTask1.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.task;


import com.cdk8s.sculptor.service.SysUserService;
import com.cdk8s.sculptor.util.StringUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * cdk8stodo 预留功能
 * 由于 implements SchedulingConfigurer 这里是多线程执行调度任务，要考虑并发问题
 */
@Slf4j
@Component
public class DynamicTask1 implements SchedulingConfigurer {

	@Autowired
	private SysUserService sysUserService;

	@Setter
	@Getter
	private String cron = "0 0/30 * * * *";

	@Override
	public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
		scheduledTaskRegistrar.addTriggerTask(doTask(), getTrigger());
	}

	/**
	 * 业务执行方法
	 */
	private Runnable doTask() {
		return new Runnable() {
			@Override
			public void run() {
				try {
					//写自己定时要执行的任务
					doMethod();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
	}

	/**
	 * 业务触发器
	 */
	private Trigger getTrigger() {
		return new Trigger() {
			@Override
			public Date nextExecutionTime(TriggerContext triggerContext) {
				if (StringUtil.isBlank(cron)) {
					return null;
				}

				log.info("------zch------ <{},cron={}>", Thread.currentThread().getId(), cron);

				// 触发器
				CronTrigger trigger = new CronTrigger(cron);
				return trigger.nextExecutionTime(triggerContext);
			}
		};
	}

	/**
	 * 实际业务要执行的方法
	 */
	@SneakyThrows
	private void doMethod() {
		log.info("------zch------ <{},sysUserService={}>", Thread.currentThread().getId(), sysUserService.toString());
	}


}
