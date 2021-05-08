/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysFolderInfoMapper.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.*;
import com.cdk8s.sculptor.pojo.bo.mapper.sysfolderinfo.SysFolderInfoFolderNameListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysfolderinfo.SysFolderInfoFolderNameMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysfolderinfo.SysFolderInfoPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.entity.SysFolderInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysFolderInfoMapper extends CustomBaseMapper<SysFolderInfo> {

	// =====================================查询业务 start=====================================

	SysFolderInfo selectById(IdMapperBO mapperBO);


	List<SysFolderInfo> selectByFolderName(SysFolderInfoFolderNameMapperBO mapperBO);


	List<SysFolderInfo> selectByFolderNameList(SysFolderInfoFolderNameListMapperBO mapperBO);


	List<SysFolderInfo> selectByParentId(ParentIdMapperBO mapperBO);

	List<SysFolderInfo> selectByParentIdList(ParentIdListMapperBO mapperBO);


	List<SysFolderInfo> selectByIdList(IdListMapperBO mapperBO);

	List<SysFolderInfo> selectByPageQueryMapperBo(SysFolderInfoPageQueryMapperBO mapperBO);

	List<SysFolderInfo> selectByStateEnum(BaseQueryMapperBO mapperBO);

	List<SysFolderInfo> selectByDeleteEnum(BaseQueryMapperBO mapperBO);

	List<SysFolderInfo> selectByDeleteEnumAndStateEnum(BaseQueryMapperBO mapperBO);

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<SysFolderInfo> list);

	int updateStateEnumByIdList(BatchUpdateStateMapperBO mapperBO);

	int updateDeleteEnumByIdList(BatchDeleteMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
