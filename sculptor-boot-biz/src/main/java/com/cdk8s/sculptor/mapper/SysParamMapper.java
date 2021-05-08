/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysParamMapper.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.*;
import com.cdk8s.sculptor.pojo.bo.mapper.sysparam.*;
import com.cdk8s.sculptor.pojo.entity.SysParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysParamMapper extends CustomBaseMapper<SysParam> {

	// =====================================查询业务 start=====================================

	SysParam selectById(IdMapperBO mapperBO);


	SysParam selectOneByTypeId(SysParamTypeIdMapperBO mapperBO);

	List<SysParam> selectByTypeId(SysParamTypeIdMapperBO mapperBO);

	List<SysParam> selectByTypeIdList(SysParamTypeIdListMapperBO mapperBO);


	List<SysParam> selectByParamCode(SysParamParamCodeMapperBO mapperBO);

	List<SysParam> selectByTypeCode(SysParamTypeCodeMapperBO mapperBO);


	List<SysParam> selectByParamCodeList(SysParamParamCodeListMapperBO mapperBO);

	List<SysParam> selectByTypeCodeList(SysParamTypeCodeListMapperBO mapperBO);


	List<SysParam> selectByIdList(IdListMapperBO mapperBO);

	List<SysParam> selectByPageQueryMapperBo(SysParamPageQueryMapperBO mapperBO);

	List<SysParam> selectByStateEnum(BaseQueryMapperBO mapperBO);

	List<SysParam> selectByDeleteEnum(BaseQueryMapperBO mapperBO);

	List<SysParam> selectByDeleteEnumAndStateEnum(BaseQueryMapperBO mapperBO);

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<SysParam> list);

	int updateStateEnumByIdList(BatchUpdateStateMapperBO mapperBO);

	int updateDeleteEnumByIdList(BatchDeleteMapperBO mapperBO);

	int updateDeleteEnumByTypeIdList(SysParamTypeIdListToDeleteMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
