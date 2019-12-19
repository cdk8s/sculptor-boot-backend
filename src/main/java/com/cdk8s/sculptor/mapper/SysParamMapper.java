package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.*;
import com.cdk8s.sculptor.pojo.bo.mapper.sysparam.SysParamPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysparam.SysParamParamCodeMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysparam.SysParamTypeCodeMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysparam.SysParamTypeIdMapperBO;
import com.cdk8s.sculptor.pojo.entity.SysParam;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysParamMapper extends CustomBaseMapper<SysParam> {

	// =====================================查询业务 start=====================================

	SysParam selectById(IdMapperBO mapperBO);

	SysParam selectOneByTypeId(SysParamTypeIdMapperBO mapperBO);

	SysParam selectOneByParamCode(SysParamParamCodeMapperBO mapperBO);

	List<SysParam> selectByTypeId(SysParamTypeIdMapperBO mapperBO);

	List<SysParam> selectByTypeIdList(SysParamTypeIdMapperBO mapperBO);

	List<SysParam> selectByTypeCode(SysParamTypeCodeMapperBO mapperBO);

	List<SysParam> selectByTypeCodeList(SysParamTypeCodeMapperBO mapperBO);

	List<SysParam> selectByParamCodeList(SysParamParamCodeMapperBO mapperBO);

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

	int updateDeleteEnumByTypeIdList(SysParamTypeIdMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
