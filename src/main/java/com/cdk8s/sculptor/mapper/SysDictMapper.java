package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.*;
import com.cdk8s.sculptor.pojo.bo.mapper.sysdict.SysDictDictCodeMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysdict.SysDictPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.entity.SysDict;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysDictMapper extends CustomBaseMapper<SysDict> {

	// =====================================查询业务 start=====================================

	SysDict selectById(IdMapperBO mapperBO);


	List<SysDict> selectByDictCode(SysDictDictCodeMapperBO mapperBO);


	List<SysDict> selectByDictCodeList(SysDictDictCodeMapperBO mapperBO);


	List<SysDict> selectByIdList(IdListMapperBO mapperBO);

	List<SysDict> selectByPageQueryMapperBo(SysDictPageQueryMapperBO mapperBO);

	List<SysDict> selectByStateEnum(BaseQueryMapperBO mapperBO);

	List<SysDict> selectByDeleteEnum(BaseQueryMapperBO mapperBO);

	List<SysDict> selectByDeleteEnumAndStateEnum(BaseQueryMapperBO mapperBO);

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<SysDict> list);

	int updateStateEnumByIdList(BatchUpdateStateMapperBO mapperBO);

	int updateDeleteEnumByIdList(BatchDeleteMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
