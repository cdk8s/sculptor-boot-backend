package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.relpermissionrole.RelPermissionRolePageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.relpermissionrole.RelPermissionRolePermissionIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.relpermissionrole.RelPermissionRoleRoleIdMapperBO;
import com.cdk8s.sculptor.pojo.entity.RelPermissionRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RelPermissionRoleMapper extends CustomBaseMapper<RelPermissionRole> {

	// =====================================查询业务 start=====================================

	RelPermissionRole selectById(IdMapperBO mapperBO);

	RelPermissionRole selectOneByPermissionId(RelPermissionRolePermissionIdMapperBO mapperBO);

	List<RelPermissionRole> selectByPermissionId(RelPermissionRolePermissionIdMapperBO mapperBO);

	List<RelPermissionRole> selectByPermissionIdList(RelPermissionRolePermissionIdMapperBO mapperBO);

	RelPermissionRole selectOneByRoleId(RelPermissionRoleRoleIdMapperBO mapperBO);

	List<RelPermissionRole> selectByRoleId(RelPermissionRoleRoleIdMapperBO mapperBO);

	List<RelPermissionRole> selectByRoleIdList(RelPermissionRoleRoleIdMapperBO mapperBO);


	List<RelPermissionRole> selectByIdList(IdListMapperBO mapperBO);

	List<RelPermissionRole> selectByPageQueryMapperBo(RelPermissionRolePageQueryMapperBO mapperBO);


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<RelPermissionRole> list);


	int deleteByIdList(BatchDeleteMapperBO mapperBO);

	int deleteByPermissionIdList(RelPermissionRolePermissionIdMapperBO mapperBO);

	int deleteByRoleIdList(RelPermissionRoleRoleIdMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
