/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysSmsLoginLogMapper.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.syssmsloginlog.*;
import com.cdk8s.sculptor.pojo.entity.SysSmsLoginLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysSmsLoginLogMapper extends CustomBaseMapper<SysSmsLoginLog> {

	// =====================================查询业务 start=====================================

	SysSmsLoginLog selectById(IdMapperBO mapperBO);


	SysSmsLoginLog selectOneByUserId(SysSmsLoginLogUserIdMapperBO mapperBO);

	List<SysSmsLoginLog> selectByUserId(SysSmsLoginLogUserIdMapperBO mapperBO);

	List<SysSmsLoginLog> selectByUserIdList(SysSmsLoginLogUserIdListMapperBO mapperBO);

	SysSmsLoginLog selectOneByUserMobilePhone(SysSmsLoginLogUserMobilePhoneMapperBO mapperBO);

	List<SysSmsLoginLog> selectByUserMobilePhone(SysSmsLoginLogUserMobilePhoneMapperBO mapperBO);

	List<SysSmsLoginLog> selectByUserMobilePhoneList(SysSmsLoginLogUserMobilePhoneListMapperBO mapperBO);


	List<SysSmsLoginLog> selectByIdList(IdListMapperBO mapperBO);

	List<SysSmsLoginLog> selectByPageQueryMapperBo(SysSmsLoginLogPageQueryMapperBO mapperBO);


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<SysSmsLoginLog> list);


	int deleteByIdList(BatchDeleteMapperBO mapperBO);

	int deleteByUserIdList(SysSmsLoginLogUserIdListToDeleteMapperBO mapperBO);

	int deleteByUserMobilePhoneList(SysSmsLoginLogUserMobilePhoneListToDeleteMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
