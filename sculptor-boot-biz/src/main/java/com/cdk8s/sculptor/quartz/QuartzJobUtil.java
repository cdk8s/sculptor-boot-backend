/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：QuartzJobUtil.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.quartz;


import com.cdk8s.sculptor.enums.BooleanEnum;
import com.cdk8s.sculptor.enums.MisfirePolicyEnum;
import com.cdk8s.sculptor.enums.StateEnum;
import com.cdk8s.sculptor.exception.TaskException;
import com.cdk8s.sculptor.pojo.entity.SysJob;
import org.quartz.*;

/**
 * 定时任务工具类
 */
public class QuartzJobUtil {

	/**
	 * 得到quartz任务类
	 *
	 * @return 具体执行任务类
	 */
	private static Class<? extends Job> getQuartzJobClass(SysJob sysJob) {
		if (sysJob.getBoolSupportConcurrentEnum().equals(BooleanEnum.YES.getCode())) {
			return QuartzJobConcurrentExecution.class;
		}
		return QuartzJobDisallowConcurrentExecution.class;
	}

	/**
	 * 构建任务触发对象
	 */
	public static TriggerKey getTriggerKey(Long jobId, String jobGroup) {
		return TriggerKey.triggerKey(QuartzJobConstant.JOB_NAME_PREFIX + jobId, jobGroup);
	}

	/**
	 * 构建任务键对象
	 */
	public static JobKey getJobKey(Long jobId, String jobGroup) {
		return JobKey.jobKey(QuartzJobConstant.JOB_NAME_PREFIX + jobId, jobGroup);
	}

	/**
	 * 创建定时任务
	 */
	public static void createScheduleJob(Scheduler scheduler, SysJob sysJob) throws SchedulerException, TaskException {
		Class<? extends Job> jobClass = getQuartzJobClass(sysJob);
		// 构建job信息
		Long jobId = sysJob.getId();
		String jobGroup = sysJob.getJobGroup();
		JobDetail jobDetail = JobBuilder.newJob(jobClass).withIdentity(getJobKey(jobId, jobGroup)).build();

		// 表达式调度构建器
		CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(sysJob.getCronExpression());
		cronScheduleBuilder = handleCronScheduleMisfirePolicy(sysJob, cronScheduleBuilder);

		// 按新的cronExpression表达式构建一个新的trigger
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(getTriggerKey(jobId, jobGroup)).withSchedule(cronScheduleBuilder).build();

		// 放入参数，运行时的方法可以获取
		jobDetail.getJobDataMap().put(QuartzJobConstant.JOB_DATA_MAP_KEY, sysJob);

		// 判断是否存在
		if (scheduler.checkExists(getJobKey(jobId, jobGroup))) {
			// 防止创建时存在数据问题 先移除，然后在执行创建操作
			scheduler.deleteJob(getJobKey(jobId, jobGroup));
		}

		scheduler.scheduleJob(jobDetail, trigger);

		// 暂停任务，如果是暂停的任务依旧保持暂停
		if (sysJob.getStateEnum().equals(StateEnum.DISABLE.getCode())) {
			scheduler.pauseJob(QuartzJobUtil.getJobKey(jobId, jobGroup));
		}
	}

	/**
	 * 更新定时任务策略
	 */
	public static void rescheduleScheduleJob(Scheduler scheduler, SysJob job) throws SchedulerException, TaskException {
		Long jobId = job.getId();
		// 判断是否存在
		JobKey jobKey = QuartzJobUtil.getJobKey(jobId, job.getJobGroup());
		if (scheduler.checkExists(jobKey)) {
			// 防止创建时存在数据问题 先移除，然后在执行创建操作
			scheduler.deleteJob(jobKey);
		}
		createScheduleJob(scheduler, job);
	}

	/**
	 * 设置定时任务策略
	 */
	private static CronScheduleBuilder handleCronScheduleMisfirePolicy(SysJob job, CronScheduleBuilder cb) throws TaskException {
		MisfirePolicyEnum enumByCode = MisfirePolicyEnum.getEnumByCode(job.getMisfirePolicyEnum());
		switch (enumByCode) {
			case DEFAULT:
				return cb;
			case NOW_RUN:
				return cb.withMisfireHandlingInstructionIgnoreMisfires();
			case ONLY_RUN:
				return cb.withMisfireHandlingInstructionFireAndProceed();
			case SUSPEND_RUN:
				return cb.withMisfireHandlingInstructionDoNothing();
			default:
				throw new TaskException("The task misfire policy '" + job.getMisfirePolicyEnum() + "' cannot be used in cron schedule tasks", TaskException.Code.CONFIG_ERROR);
		}
	}
}
