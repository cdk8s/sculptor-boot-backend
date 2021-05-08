/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysSmsLoginLogService.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.service;

import com.cdk8s.sculptor.enums.BooleanEnum;
import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.mapper.SysSmsLoginLogMapper;
import com.cdk8s.sculptor.mapper.ext.SysSmsLoginLogMapperExt;
import com.cdk8s.sculptor.mapstruct.SysSmsLoginLogMapStruct;
import com.cdk8s.sculptor.pojo.bo.service.syssmsloginlog.SysSmsLoginLogPageQueryServiceBO;
import com.cdk8s.sculptor.pojo.bo.service.syssmsloginlog.SysSmsLoginLogUpdateServiceBO;
import com.cdk8s.sculptor.pojo.entity.SysSmsLoginLog;
import com.cdk8s.sculptor.service.bases.SysSmsLoginLogServiceBase;
import com.cdk8s.sculptor.util.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class SysSmsLoginLogService extends SysSmsLoginLogServiceBase {

	@Autowired
	private SysSmsLoginLogMapper sysSmsLoginLogMapper;

	@Autowired
	private SysSmsLoginLogMapperExt sysSmsLoginLogMapperExt;

	@Autowired
	private SysSmsLoginLogMapStruct sysSmsLoginLogMapStruct;

	// =====================================查询业务 start=====================================

	@Transactional(readOnly = true)
	public Boolean checkCodeIsTrue(String userMobilePhone, String verificationCode) {
		SysSmsLoginLogPageQueryServiceBO serviceBO = new SysSmsLoginLogPageQueryServiceBO();
		serviceBO.setUserMobilePhone(userMobilePhone);
		serviceBO.setVerificationCode(verificationCode);
		serviceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		List<SysSmsLoginLog> listByServiceBO = findListByServiceBO(serviceBO);
		if (CollectionUtil.isEmpty(listByServiceBO)) {
			return false;
		}

		if (listByServiceBO.get(0).getBoolUseEnum().equals(BooleanEnum.YES.getCode())) {
			return false;
		}

		return true;
	}

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	@Transactional(rollbackFor = Exception.class)
	public void updateCodeIsUse(String userMobilePhone, String verificationCode) {
		SysSmsLoginLogPageQueryServiceBO serviceBO = new SysSmsLoginLogPageQueryServiceBO();
		serviceBO.setUserMobilePhone(userMobilePhone);
		serviceBO.setVerificationCode(verificationCode);
		serviceBO.setDeleteEnum(DeleteEnum.NOT_DELETED.getCode());
		List<SysSmsLoginLog> listByServiceBO = findListByServiceBO(serviceBO);
		if (CollectionUtil.isEmpty(listByServiceBO)) {
			return;
		}

		SysSmsLoginLog sysSmsLoginLog = listByServiceBO.get(0);
		SysSmsLoginLogUpdateServiceBO sysSmsLoginLogUpdateServiceBO = new SysSmsLoginLogUpdateServiceBO();
		sysSmsLoginLogUpdateServiceBO.setId(sysSmsLoginLog.getId());
		sysSmsLoginLogUpdateServiceBO.setBoolUseEnum(BooleanEnum.YES.getCode());
		update(sysSmsLoginLogUpdateServiceBO);
	}

	// =====================================操作业务 end=====================================
	// =====================================私有方法 start=====================================

	// =====================================私有方法 end=====================================

}

