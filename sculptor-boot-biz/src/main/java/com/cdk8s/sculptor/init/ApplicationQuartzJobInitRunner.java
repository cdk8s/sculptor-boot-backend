/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：ApplicationQuartzJobInitRunner.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.init;

import com.cdk8s.sculptor.enums.StateEnum;
import com.cdk8s.sculptor.exception.TaskException;
import com.cdk8s.sculptor.mapper.SysJobMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.sysjob.SysJobPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.entity.SysJob;
import com.cdk8s.sculptor.quartz.QuartzJobUtil;
import com.github.pagehelper.PageHelper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Order(25)
@Component
public class ApplicationQuartzJobInitRunner implements ApplicationRunner {

	@Autowired
	private SysJobMapper sysJobMapper;

	@Autowired
	private Scheduler scheduler;

	//=====================================业务处理 start=====================================

	/**
	 * 项目启动时，初始化定时器
	 * 主要是防止手动修改数据库导致未同步到定时任务处理（注：不能手动修改数据库ID和任务组名，否则会导致脏数据）
	 */
	@SneakyThrows
	@Override
	public void run(ApplicationArguments args) {

		PageHelper.startPage(1, 500);

		SysJobPageQueryMapperBO mapperBO = new SysJobPageQueryMapperBO();
		mapperBO.setStateEnum(StateEnum.ENABLE.getCode());

		List<SysJob> jobList = sysJobMapper.selectByPageQueryMapperBo(mapperBO);
		for (SysJob job : jobList) {
			initSchedulerJob(job, job.getJobGroup());
		}

	}

	//=====================================业务处理  end=====================================
	//=====================================私有方法 start=====================================

	private void initSchedulerJob(SysJob job, String jobGroup) throws SchedulerException, TaskException {
		Long jobId = job.getId();
		// 判断是否存在
		JobKey jobKey = QuartzJobUtil.getJobKey(jobId, jobGroup);
		if (scheduler.checkExists(jobKey)) {
			// 防止创建时存在数据问题 先移除，然后在执行创建操作
			scheduler.deleteJob(jobKey);
		}
		QuartzJobUtil.createScheduleJob(scheduler, job);
	}

	//=====================================私有方法  end=====================================

}
