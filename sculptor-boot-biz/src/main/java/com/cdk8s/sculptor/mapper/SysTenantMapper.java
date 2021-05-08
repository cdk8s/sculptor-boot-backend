/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysTenantMapper.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.*;
import com.cdk8s.sculptor.pojo.bo.mapper.systenant.SysTenantPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.systenant.SysTenantTenantCodeListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.systenant.SysTenantTenantCodeMapperBO;
import com.cdk8s.sculptor.pojo.entity.SysTenant;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysTenantMapper extends CustomBaseMapper<SysTenant> {

	// =====================================查询业务 start=====================================

	SysTenant selectById(IdMapperBO mapperBO);


	List<SysTenant> selectByTenantCode(SysTenantTenantCodeMapperBO mapperBO);


	List<SysTenant> selectByTenantCodeList(SysTenantTenantCodeListMapperBO mapperBO);


	List<SysTenant> selectByIdList(IdListMapperBO mapperBO);

	List<SysTenant> selectByPageQueryMapperBo(SysTenantPageQueryMapperBO mapperBO);

	List<SysTenant> selectByStateEnum(BaseQueryMapperBO mapperBO);

	List<SysTenant> selectByDeleteEnum(BaseQueryMapperBO mapperBO);

	List<SysTenant> selectByDeleteEnumAndStateEnum(BaseQueryMapperBO mapperBO);

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<SysTenant> list);

	int updateStateEnumByIdList(BatchUpdateStateMapperBO mapperBO);

	int updateDeleteEnumByIdList(BatchDeleteMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
