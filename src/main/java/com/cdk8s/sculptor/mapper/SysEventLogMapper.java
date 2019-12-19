package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.BatchDeleteMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.syseventlog.SysEventLogPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.syseventlog.SysEventLogUserIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.syseventlog.SysEventLogUsernameMapperBO;
import com.cdk8s.sculptor.pojo.entity.SysEventLog;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysEventLogMapper extends CustomBaseMapper<SysEventLog> {

	// =====================================查询业务 start=====================================

	SysEventLog selectById(IdMapperBO mapperBO);

	SysEventLog selectOneByUserId(SysEventLogUserIdMapperBO mapperBO);

	List<SysEventLog> selectByUserId(SysEventLogUserIdMapperBO mapperBO);

	List<SysEventLog> selectByUserIdList(SysEventLogUserIdMapperBO mapperBO);


	List<SysEventLog> selectByUsername(SysEventLogUsernameMapperBO mapperBO);


	List<SysEventLog> selectByUsernameList(SysEventLogUsernameMapperBO mapperBO);


	List<SysEventLog> selectByIdList(IdListMapperBO mapperBO);

	List<SysEventLog> selectByPageQueryMapperBo(SysEventLogPageQueryMapperBO mapperBO);


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<SysEventLog> list);


	int deleteByIdList(BatchDeleteMapperBO mapperBO);

	int deleteByUserIdList(SysEventLogUserIdMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
