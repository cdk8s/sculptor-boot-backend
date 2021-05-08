/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：AbstractQuartzJob.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.quartz;


import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.BooleanEnum;
import com.cdk8s.sculptor.eventlistener.event.JobLogEvent;
import com.cdk8s.sculptor.pojo.bo.service.sysjoblog.SysJobLogCreateServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysJob;
import com.cdk8s.sculptor.util.*;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**
 * 抽象 quartz 调用周期
 */
@Slf4j
public abstract class AbstractQuartzJob implements Job {

	/**
	 * 线程本地变量
	 */
	private static ThreadLocal<QuartzJobExecutionBO> threadLocal = new ThreadLocal<>();

	/**
	 * 执行流程控制
	 *
	 * @param context 工作执行上下文对象
	 */
	@Override
	public void execute(JobExecutionContext context) {
		SysJob sysJob = new SysJob();
		BeanUtil.copyProperties(context.getMergedJobDataMap().get(QuartzJobConstant.JOB_DATA_MAP_KEY), sysJob);

		try {
			beforeExecute(context, sysJob);
			doExecute(context, sysJob);
			afterExecute(context, sysJob, null);
		} catch (Exception e) {
			log.error("任务执行异常：{}", ExceptionUtil.getStackTraceAsString(e));
			afterExecute(context, sysJob, e);
		}
	}

	/**
	 * 执行前
	 */
	private void beforeExecute(JobExecutionContext context, SysJob sysJob) {
		// 记录开始时间
		QuartzJobExecutionBO bo = new QuartzJobExecutionBO();
		bo.setStartDate(DatetimeUtil.currentEpochMilli());
		threadLocal.set(bo);
	}

	/**
	 * 执行后
	 */
	private void afterExecute(JobExecutionContext context, SysJob sysJob, Exception e) {
		QuartzJobExecutionBO bo = threadLocal.get();
		threadLocal.remove();

		long currentEpochMilli = DatetimeUtil.currentEpochMilli();
		long executeTime = currentEpochMilli - bo.getStartDate();
		SysJobLogCreateServiceBO serviceBO = new SysJobLogCreateServiceBO();
		serviceBO.setTenantId(GlobalConstant.TOP_TENANT_ID);
		serviceBO.setCreateDate(currentEpochMilli);
		serviceBO.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		serviceBO.setJobId(sysJob.getId());
		serviceBO.setJobName(sysJob.getJobName());
		serviceBO.setJobGroup(sysJob.getJobGroup());
		serviceBO.setInvokeTarget(sysJob.getInvokeTarget());
		serviceBO.setCronExpression(sysJob.getCronExpression());
		serviceBO.setJobMessage(serviceBO.getJobName() + " 总共耗时：" + executeTime + "毫秒");
		serviceBO.setDescription(serviceBO.getJobName() + " 总共耗时：" + executeTime + "毫秒");
		serviceBO.setBoolExecuteSuccessEnum(BooleanEnum.YES.getCode());
		serviceBO.setJobStartDate(bo.getStartDate());
		serviceBO.setJobEndDate(currentEpochMilli);
		serviceBO.setExecuteTime(executeTime);

		if (e != null) {
			serviceBO.setBoolExecuteSuccessEnum(BooleanEnum.NO.getCode());
			String errorMsg = StringUtil.substring(ExceptionUtil.getStackTraceAsString(e), 0, 250);
			serviceBO.setExceptionMsg(errorMsg);
		}

		SpringUtil.getApplicationContext().publishEvent(new JobLogEvent(this, serviceBO));
	}

	/**
	 * 执行方法，由子类重载
	 */
	protected abstract void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception;
}
