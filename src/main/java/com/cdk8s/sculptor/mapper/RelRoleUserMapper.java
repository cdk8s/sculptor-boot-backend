package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.relroleuser.RelRoleUserPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.relroleuser.RelRoleUserRoleIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.relroleuser.RelRoleUserUserIdAndRoleIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.relroleuser.RelRoleUserUserIdMapperBO;
import com.cdk8s.sculptor.pojo.entity.RelRoleUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RelRoleUserMapper extends CustomBaseMapper<RelRoleUser> {

	// =====================================查询业务 start=====================================

	RelRoleUser selectById(IdMapperBO mapperBO);

	RelRoleUser selectOneByRoleId(RelRoleUserRoleIdMapperBO mapperBO);

	List<RelRoleUser> selectByRoleId(RelRoleUserRoleIdMapperBO mapperBO);

	List<RelRoleUser> selectByRoleIdList(RelRoleUserRoleIdMapperBO mapperBO);

	RelRoleUser selectOneByUserId(RelRoleUserUserIdMapperBO mapperBO);

	List<RelRoleUser> selectByUserId(RelRoleUserUserIdMapperBO mapperBO);

	List<RelRoleUser> selectByUserIdList(RelRoleUserUserIdMapperBO mapperBO);

	List<RelRoleUser> selectByUserIdAndRoleId(RelRoleUserUserIdAndRoleIdMapperBO mapperBO);

	List<RelRoleUser> selectByIdList(IdListMapperBO mapperBO);

	List<RelRoleUser> selectByPageQueryMapperBo(RelRoleUserPageQueryMapperBO mapperBO);


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<RelRoleUser> list);


	int deleteByIdList(BatchDeleteMapperBO mapperBO);

	int deleteByRoleIdList(RelRoleUserRoleIdMapperBO mapperBO);

	int deleteByUserIdList(RelRoleUserUserIdMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
