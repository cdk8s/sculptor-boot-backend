/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：QuartzJobDisallowConcurrentExecution.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.quartz;


import com.cdk8s.sculptor.pojo.entity.SysJob;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;

/**
 * 定时任务处理（禁止并发执行）
 *
 * @DisallowConcurrentExecution 注解的作用：
 * 此标记用在实现Job的类上面,意思是不允许并发执行,按照我之前的理解是 不允许调度框架在同一时刻调用Job类，后来经过测试发现并不是这样，而是Job(任务)的执行时间[比如需要10秒]大于任务的时间间隔[Interval（5秒)],
 * 那么默认情况下,调度框架为了能让 任务按照我们预定的时间间隔执行,会马上启用新的线程执行任务。否则的话会等待任务执行完毕以后 再重新执行！（这样会导致任务的执行不是按照我们预先定义的时间间隔执行）
 * 设置 @DisallowConcurrentExecution 以后程序会等任务执行完毕以后再去执行，这样就不会再启用新的线程执行
 * 资料：http://www.cnblogs.com/daxin/archive/2013/05/27/3101972.html
 */
@DisallowConcurrentExecution
public class QuartzJobDisallowConcurrentExecution extends AbstractQuartzJob {
	@Override
	protected void doExecute(JobExecutionContext context, SysJob sysJob) throws Exception {
		QuartzJobInvokeUtil.invokeMethod(sysJob);
	}
}
