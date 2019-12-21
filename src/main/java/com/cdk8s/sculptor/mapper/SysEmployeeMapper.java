package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysemployee.SysEmployeeBatchDeleteByUserIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysemployee.SysEmployeePageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysemployee.SysEmployeeUserIdMapperBO;
import com.cdk8s.sculptor.pojo.entity.SysEmployee;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysEmployeeMapper extends CustomBaseMapper<SysEmployee> {

	// =====================================查询业务 start=====================================

	SysEmployee selectById(IdMapperBO mapperBO);

	SysEmployee selectOneByUserId(SysEmployeeUserIdMapperBO mapperBO);

	List<SysEmployee> selectByUserId(SysEmployeeUserIdMapperBO mapperBO);

	List<SysEmployee> selectByUserIdList(SysEmployeeUserIdMapperBO mapperBO);


	List<SysEmployee> selectByIdList(IdListMapperBO mapperBO);

	List<SysEmployee> selectByPageQueryMapperBo(SysEmployeePageQueryMapperBO mapperBO);

	List<SysEmployee> selectByPageQueryMapperBoToUser(SysEmployeePageQueryMapperBO mapperBO);

	List<SysEmployee> selectByDeleteEnum(BaseQueryMapperBO mapperBO);


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<SysEmployee> list);


	int updateDeleteEnumByIdList(BatchDeleteMapperBO mapperBO);

	int updateDeleteEnumByUserIdList(SysEmployeeBatchDeleteByUserIdMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
