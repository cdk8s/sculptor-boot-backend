package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.*;
import com.cdk8s.sculptor.pojo.bo.mapper.sysdept.SysDeptDeptCodeMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysdept.SysDeptPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.entity.SysDept;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysDeptMapper extends CustomBaseMapper<SysDept> {

	// =====================================查询业务 start=====================================

	SysDept selectById(IdMapperBO mapperBO);


	List<SysDept> selectByDeptCode(SysDeptDeptCodeMapperBO mapperBO);


	List<SysDept> selectByDeptCodeList(SysDeptDeptCodeMapperBO mapperBO);


	List<SysDept> selectByParentId(ParentIdMapperBO mapperBO);

	List<SysDept> selectByParentIdList(ParentIdMapperBO mapperBO);


	List<SysDept> selectByIdList(IdListMapperBO mapperBO);

	List<SysDept> selectByPageQueryMapperBo(SysDeptPageQueryMapperBO mapperBO);

	List<SysDept> selectByStateEnum(BaseQueryMapperBO mapperBO);

	List<SysDept> selectByDeleteEnum(BaseQueryMapperBO mapperBO);

	List<SysDept> selectByDeleteEnumAndStateEnum(BaseQueryMapperBO mapperBO);

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<SysDept> list);

	int updateStateEnumByIdList(BatchUpdateStateMapperBO mapperBO);

	int updateDeleteEnumByIdList(BatchDeleteMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
