package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysloginlog.SysLoginLogPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysloginlog.SysLoginLogTokenMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysloginlog.SysLoginLogUserIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysloginlog.SysLoginLogUsernameMapperBO;
import com.cdk8s.sculptor.pojo.entity.SysLoginLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysLoginLogMapper extends CustomBaseMapper<SysLoginLog> {

	// =====================================查询业务 start=====================================

	SysLoginLog selectById(IdMapperBO mapperBO);

	SysLoginLog selectOneByToken(SysLoginLogTokenMapperBO mapperBO);

	SysLoginLog selectOneByUserId(SysLoginLogUserIdMapperBO mapperBO);

	List<SysLoginLog> selectByUserId(SysLoginLogUserIdMapperBO mapperBO);

	List<SysLoginLog> selectByUserIdList(SysLoginLogUserIdMapperBO mapperBO);

	List<SysLoginLog> selectByUsername(SysLoginLogUsernameMapperBO mapperBO);

	List<SysLoginLog> selectByUsernameList(SysLoginLogUsernameMapperBO mapperBO);

	List<SysLoginLog> selectByIdList(IdListMapperBO mapperBO);

	List<SysLoginLog> selectByPageQueryMapperBo(SysLoginLogPageQueryMapperBO mapperBO);

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<SysLoginLog> list);

	int deleteByIdList(BatchDeleteMapperBO mapperBO);

	int deleteByUserIdList(SysLoginLogUserIdMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
