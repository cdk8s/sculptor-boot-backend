package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.*;
import com.cdk8s.sculptor.pojo.bo.mapper.sysuser.SysUserMobilePhoneMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysuser.SysUserPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysuser.SysUserUserEmailMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysuser.SysUserUsernameMapperBO;
import com.cdk8s.sculptor.pojo.entity.SysUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysUserMapper extends CustomBaseMapper<SysUser> {

	// =====================================查询业务 start=====================================

	SysUser selectById(IdMapperBO mapperBO);


	SysUser selectOneByUsername(SysUserUsernameMapperBO mapperBO);

	List<SysUser> selectByMobilePhone(SysUserMobilePhoneMapperBO mapperBO);

	List<SysUser> selectByUserEmail(SysUserUserEmailMapperBO mapperBO);

	List<SysUser> selectByIdList(IdListMapperBO mapperBO);

	List<SysUser> selectByPageQueryMapperBo(SysUserPageQueryMapperBO mapperBO);

	List<SysUser> selectByPageQueryMapperBoToDept(SysUserPageQueryMapperBO mapperBO);

	List<SysUser> selectByPageQueryMapperBoToRole(SysUserPageQueryMapperBO mapperBO);

	List<SysUser> selectByStateEnum(BaseQueryMapperBO mapperBO);

	List<SysUser> selectByDeleteEnum(BaseQueryMapperBO mapperBO);

	List<SysUser> selectByDeleteEnumAndStateEnum(BaseQueryMapperBO mapperBO);

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<SysUser> list);

	int updateStateEnumByIdList(BatchUpdateStateMapperBO mapperBO);

	int updateDeleteEnumByIdList(BatchDeleteMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
