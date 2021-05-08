/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysJobMapper.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.*;
import com.cdk8s.sculptor.pojo.bo.mapper.sysjob.SysJobJobNameListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysjob.SysJobJobNameMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysjob.SysJobPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.entity.SysJob;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysJobMapper extends CustomBaseMapper<SysJob> {

	// =====================================查询业务 start=====================================

	SysJob selectById(IdMapperBO mapperBO);


	List<SysJob> selectByJobName(SysJobJobNameMapperBO mapperBO);


	List<SysJob> selectByJobNameList(SysJobJobNameListMapperBO mapperBO);


	List<SysJob> selectByIdList(IdListMapperBO mapperBO);

	List<SysJob> selectByPageQueryMapperBo(SysJobPageQueryMapperBO mapperBO);

	List<SysJob> selectByStateEnum(BaseQueryMapperBO mapperBO);


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<SysJob> list);

	int updateStateEnumByIdList(BatchUpdateStateMapperBO mapperBO);

	int deleteByIdList(BatchDeleteMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
