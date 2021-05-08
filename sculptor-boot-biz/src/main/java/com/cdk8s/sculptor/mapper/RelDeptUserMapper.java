/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：RelDeptUserMapper.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.reldeptuser.*;
import com.cdk8s.sculptor.pojo.entity.RelDeptUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RelDeptUserMapper extends CustomBaseMapper<RelDeptUser> {

	// =====================================查询业务 start=====================================

	RelDeptUser selectById(IdMapperBO mapperBO);


	RelDeptUser selectOneByDeptId(RelDeptUserDeptIdMapperBO mapperBO);

	List<RelDeptUser> selectByDeptId(RelDeptUserDeptIdMapperBO mapperBO);

	List<RelDeptUser> selectByDeptIdList(RelDeptUserDeptIdListMapperBO mapperBO);

	RelDeptUser selectOneByUserId(RelDeptUserUserIdMapperBO mapperBO);

	List<RelDeptUser> selectByUserId(RelDeptUserUserIdMapperBO mapperBO);

	List<RelDeptUser> selectByUserIdList(RelDeptUserUserIdListMapperBO mapperBO);


	List<RelDeptUser> selectByIdList(IdListMapperBO mapperBO);

	List<RelDeptUser> selectByPageQueryMapperBo(RelDeptUserPageQueryMapperBO mapperBO);


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<RelDeptUser> list);


	int deleteByIdList(BatchDeleteMapperBO mapperBO);

	int deleteByDeptIdAndUserId(RelDeptUserDeptIdAndUserIdToDeleteMapperBO mapperBO);

	int deleteByDeptIdList(RelDeptUserDeptIdListToDeleteMapperBO mapperBO);

	int deleteByUserIdList(RelDeptUserUserIdListToDeleteMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
