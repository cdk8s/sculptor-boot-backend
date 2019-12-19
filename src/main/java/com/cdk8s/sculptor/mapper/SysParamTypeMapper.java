package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.*;
import com.cdk8s.sculptor.pojo.bo.mapper.sysparamtype.SysParamTypePageQueryMapperBO;
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


	List<SysParamType> selectByTypeCodeList(SysParamTypeTypeCodeMapperBO mapperBO);


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
