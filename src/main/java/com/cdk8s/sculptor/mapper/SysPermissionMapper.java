package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.*;
import com.cdk8s.sculptor.pojo.bo.mapper.syspermission.SysPermissionPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.syspermission.SysPermissionPermissionCodeMapperBO;
import com.cdk8s.sculptor.pojo.entity.SysPermission;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysPermissionMapper extends CustomBaseMapper<SysPermission> {

	// =====================================查询业务 start=====================================

	SysPermission selectById(IdMapperBO mapperBO);

	List<SysPermission> selectByPermissionCode(SysPermissionPermissionCodeMapperBO mapperBO);

	List<SysPermission> selectByPermissionCodeList(SysPermissionPermissionCodeMapperBO mapperBO);

	List<SysPermission> selectByParentId(ParentIdMapperBO mapperBO);

	List<SysPermission> selectByParentIdList(ParentIdMapperBO mapperBO);

	List<SysPermission> selectByParentIdNotButton(ParentIdMapperBO mapperBO);

	List<SysPermission> selectByParentIdAndIdListNotButton(ParentIdAndIdListMapperBO mapperBO);

	List<SysPermission> selectByIdList(IdListMapperBO mapperBO);

	List<SysPermission> selectByPageQueryMapperBo(SysPermissionPageQueryMapperBO mapperBO);

	List<SysPermission> selectByStateEnum(BaseQueryMapperBO mapperBO);

	List<SysPermission> selectByDeleteEnum(BaseQueryMapperBO mapperBO);

	List<SysPermission> selectByDeleteEnumAndStateEnum(BaseQueryMapperBO mapperBO);

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<SysPermission> list);

	int updateStateEnumByIdList(BatchUpdateStateMapperBO mapperBO);

	int updateDeleteEnumByIdList(BatchDeleteMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
