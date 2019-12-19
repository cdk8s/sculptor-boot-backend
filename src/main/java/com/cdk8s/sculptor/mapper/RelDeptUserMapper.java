package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.reldeptuser.RelDeptUserDeptIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.reldeptuser.RelDeptUserPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.reldeptuser.RelDeptUserUserIdMapperBO;
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

	List<RelDeptUser> selectByDeptIdList(RelDeptUserDeptIdMapperBO mapperBO);

	RelDeptUser selectOneByUserId(RelDeptUserUserIdMapperBO mapperBO);

	List<RelDeptUser> selectByUserId(RelDeptUserUserIdMapperBO mapperBO);

	List<RelDeptUser> selectByUserIdList(RelDeptUserUserIdMapperBO mapperBO);


	List<RelDeptUser> selectByIdList(IdListMapperBO mapperBO);

	List<RelDeptUser> selectByPageQueryMapperBo(RelDeptUserPageQueryMapperBO mapperBO);


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<RelDeptUser> list);


	int deleteByIdList(BatchDeleteMapperBO mapperBO);

	int deleteByDeptIdList(RelDeptUserDeptIdMapperBO mapperBO);

	int deleteByUserIdList(RelDeptUserUserIdMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
