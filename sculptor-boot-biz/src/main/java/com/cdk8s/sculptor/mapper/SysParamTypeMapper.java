/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysParamTypeMapper.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.*;
import com.cdk8s.sculptor.pojo.bo.mapper.sysparamtype.SysParamTypePageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysparamtype.SysParamTypeTypeCodeListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysparamtype.SysParamTypeTypeCodeMapperBO;
import com.cdk8s.sculptor.pojo.entity.SysParamType;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysParamTypeMapper extends CustomBaseMapper<SysParamType> {

	// =====================================查询业务 start=====================================

	SysParamType selectById(IdMapperBO mapperBO);


	List<SysParamType> selectByTypeCode(SysParamTypeTypeCodeMapperBO mapperBO);


	List<SysParamType> selectByTypeCodeList(SysParamTypeTypeCodeListMapperBO mapperBO);


	List<SysParamType> selectByIdList(IdListMapperBO mapperBO);

	List<SysParamType> selectByPageQueryMapperBo(SysParamTypePageQueryMapperBO mapperBO);

	List<SysParamType> selectByStateEnum(BaseQueryMapperBO mapperBO);

	List<SysParamType> selectByDeleteEnum(BaseQueryMapperBO mapperBO);

	List<SysParamType> selectByDeleteEnumAndStateEnum(BaseQueryMapperBO mapperBO);

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<SysParamType> list);

	int updateStateEnumByIdList(BatchUpdateStateMapperBO mapperBO);

	int updateDeleteEnumByIdList(BatchDeleteMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
