/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：QuartzConfig.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.quartz.config;

import org.quartz.Scheduler;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;


@Configuration
public class QuartzConfig {

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) throws IOException {
		SchedulerFactoryBean factory = new SchedulerFactoryBean();
		factory.setDataSource(dataSource);

		// quartz参数
		factory.setQuartzProperties(quartzProperties());
		factory.setSchedulerName("CustomQuartzScheduler");
		factory.setWaitForJobsToCompleteOnShutdown(true);

		// 延时启动
		factory.setStartupDelay(1);
		factory.setApplicationContextSchedulerContextKey("applicationContextKey");
		// 可选，QuartzScheduler
		// 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
		factory.setOverwriteExistingJobs(true);
		// 设置自动启动，默认为true
		factory.setAutoStartup(true);

		return factory;
	}


	@Bean
	public Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		//在quartz.properties中的属性被读取并注入后再初始化对象
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}

	/**
	 * 通过SchedulerFactoryBean获取Scheduler的实例
	 */
	@Bean(name = "scheduler")
	public Scheduler scheduler(DataSource dataSource) throws IOException {
		return schedulerFactoryBean(dataSource).getScheduler();
	}
}
