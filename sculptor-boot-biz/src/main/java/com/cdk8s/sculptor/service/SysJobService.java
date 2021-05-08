/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysJobService.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.enums.StateEnum;
import com.cdk8s.sculptor.exception.BusinessException;
import com.cdk8s.sculptor.mapper.SysJobMapper;
import com.cdk8s.sculptor.mapper.ext.SysJobMapperExt;
import com.cdk8s.sculptor.mapstruct.SysJobMapStruct;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchUpdateStateMapperBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.BatchDeleteServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.bases.IdListServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysjob.SysJobCreateServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.sysjob.SysJobUpdateServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysJob;
import com.cdk8s.sculptor.quartz.QuartzJobConstant;
import com.cdk8s.sculptor.quartz.QuartzJobUtil;
import com.cdk8s.sculptor.service.bases.SysJobServiceBase;
import com.cdk8s.sculptor.strategy.UserInfoContext;
import com.cdk8s.sculptor.util.CollectionUtil;
import com.cdk8s.sculptor.util.DatetimeUtil;
import com.cdk8s.sculptor.util.id.GenerateIdUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.quartz.JobDataMap;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@CacheConfig(cacheNames = "SysJobService")
@Service
public class SysJobService extends SysJobServiceBase {

	@Autowired
	private SysJobMapper sysJobMapper;

	@Autowired
	private SysJobMapperExt sysJobMapperExt;

	@Autowired
	private SysJobMapStruct sysJobMapStruct;

	@Autowired
	private Scheduler scheduler;

	// =====================================查询业务 start=====================================


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@SneakyThrows
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer create(SysJobCreateServiceBO serviceBO) {
		if (!checkCronExpression(serviceBO.getCronExpression())) {
			throw new BusinessException("cron 执行表达式格式不正确");
		}
		SysJob entity = initCreateBasicParam(serviceBO);
		if (null == entity.getCreateUserId()) {
			entity.setCreateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}
		if (null == entity.getUpdateUserId()) {
			entity.setUpdateUserId(GlobalConstant.TOP_ADMIN_USER_ID);
		}

		entity.setStateEnum(StateEnum.DISABLE.getCode()); // 默认不启动

		int result = sysJobMapper.insert(entity);
		if (result > 0) {
			QuartzJobUtil.createScheduleJob(scheduler, entity);
		}

		return result;
	}

	@SneakyThrows
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer update(SysJobUpdateServiceBO serviceBO) {
		if (!checkCronExpression(serviceBO.getCronExpression())) {
			throw new BusinessException("cron 执行表达式格式不正确");
		}
		SysJob entity = initUpdateBasicParam(serviceBO);
		int result = sysJobMapper.updateByPrimaryKeySelective(entity);
		if (result > 0) {
			QuartzJobUtil.rescheduleScheduleJob(scheduler, entity);
		}
		return result;
	}


	/**
	 * 暂停任务
	 */
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchPauseJob(IdListServiceBO serviceBO) throws SchedulerException {
		List<SysJob> sysJobList = findListByIdList(serviceBO);
		if (CollectionUtil.isEmpty(sysJobList)) {
			return 0;
		}

		int result = sysJobMapper.updateStateEnumByIdList(initBatchUpdateStateMapperBO(StateEnum.DISABLE.getCode(), serviceBO.getIdList()));
		if (result > 0) {
			for (SysJob sysJob : sysJobList) {
				scheduler.pauseJob(QuartzJobUtil.getJobKey(sysJob.getId(), sysJob.getJobGroup()));
			}
		}
		return result;
	}

	/**
	 * 恢复任务
	 */
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchResumeJob(IdListServiceBO serviceBO) throws SchedulerException {
		List<SysJob> sysJobList = findListByIdList(serviceBO);
		if (CollectionUtil.isEmpty(sysJobList)) {
			return 0;
		}

		int result = sysJobMapper.updateStateEnumByIdList(initBatchUpdateStateMapperBO(StateEnum.ENABLE.getCode(), serviceBO.getIdList()));
		if (result > 0) {
			for (SysJob sysJob : sysJobList) {
				scheduler.resumeJob(QuartzJobUtil.getJobKey(sysJob.getId(), sysJob.getJobGroup()));
			}
		}
		return result;
	}

	@SneakyThrows
	@Transactional(rollbackFor = Exception.class)
	@CacheEvict(allEntries = true, beforeInvocation = false)
	public Integer batchDelete(BatchDeleteServiceBO serviceBO) {
		IdListServiceBO idListServiceBO = new IdListServiceBO();
		idListServiceBO.setIdList(serviceBO.getIdList());
		idListServiceBO.setTenantId(serviceBO.getTenantId());
		idListServiceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		List<SysJob> sysJobList = findListByIdList(idListServiceBO);
		if (CollectionUtil.isEmpty(sysJobList)) {
			return 0;
		}

		int result = sysJobMapper.deleteByIdList(sysJobMapStruct.batchDeleteServiceBOToMapperBO(serviceBO));
		if (result > 0) {
			for (SysJob entity : sysJobList) {
				scheduler.deleteJob(QuartzJobUtil.getJobKey(entity.getId(), entity.getJobGroup()));
			}
		}
		return result;
	}

	/**
	 * 立即运行任务
	 */
	@Transactional(rollbackFor = Exception.class)
	public void runJobNow(IdListServiceBO serviceBO) throws SchedulerException {
		List<SysJob> sysJobList = findListByIdList(serviceBO);
		if (CollectionUtil.isEmpty(sysJobList)) {
			return;
		}

		for (SysJob sysJob : sysJobList) {
			JobDataMap jobDataMap = new JobDataMap();
			jobDataMap.put(QuartzJobConstant.JOB_DATA_MAP_KEY, sysJob);
			scheduler.triggerJob(QuartzJobUtil.getJobKey(sysJob.getId(), sysJob.getJobGroup()), jobDataMap);
		}
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================


	private BatchUpdateStateMapperBO initBatchUpdateStateMapperBO(Integer stateEnum, List<Long> idList) {
		long currentEpochMilli = DatetimeUtil.currentEpochMilli();

		BatchUpdateStateMapperBO mapperBO = new BatchUpdateStateMapperBO();
		mapperBO.setIdList(idList);
		mapperBO.setStateEnum(stateEnum);
		mapperBO.setUpdateDate(currentEpochMilli);
		mapperBO.setUpdateUserId(UserInfoContext.getCurrentUserId());

		return mapperBO;
	}

	private BatchDeleteMapperBO initBatchDeleteMapperBO(List<Long> idList) {
		BatchDeleteMapperBO mapperBO = new BatchDeleteMapperBO();
		mapperBO.setIdList(idList);
		return mapperBO;
	}

	/**
	 * 校验cron表达式是否有效
	 * true 有效
	 * false 无效
	 */
	private Boolean checkCronExpression(String cronExpression) {
		return CronExpression.isValidExpression(cronExpression);
	}

	private SysJob initCreateBasicParam(SysJobCreateServiceBO serviceBO) {
		// cdk8stodo 根据业务补充其他属性
		if (null == serviceBO.getId()) {
			serviceBO.setId(GenerateIdUtil.getId());
		}

		if (null == serviceBO.getJobGroup()) {
			serviceBO.setJobGroup("DEFAULT");
		}
		if (null == serviceBO.getMisfirePolicyEnum()) {
			serviceBO.setMisfirePolicyEnum(4);
		}
		if (null == serviceBO.getBoolSupportConcurrentEnum()) {
			serviceBO.setBoolSupportConcurrentEnum(2);
		}

		if (null == serviceBO.getRanking()) {
			serviceBO.setRanking(100);
		}
		if (null == serviceBO.getStateEnum()) {
			serviceBO.setStateEnum(StateEnum.ENABLE.getCode());
		}


		return sysJobMapStruct.createServiceBOToEntity(serviceBO);
	}

	private SysJob initUpdateBasicParam(SysJobUpdateServiceBO serviceBO) {
		return sysJobMapStruct.updateServiceBOToEntity(serviceBO);
	}

	// =====================================私有方法 end=====================================

}

