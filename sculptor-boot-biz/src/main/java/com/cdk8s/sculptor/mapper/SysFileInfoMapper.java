/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysFileInfoMapper.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.*;
import com.cdk8s.sculptor.pojo.bo.mapper.sysfileinfo.*;
import com.cdk8s.sculptor.pojo.entity.SysFileInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysFileInfoMapper extends CustomBaseMapper<SysFileInfo> {

	// =====================================查询业务 start=====================================

	SysFileInfo selectById(IdMapperBO mapperBO);


	SysFileInfo selectOneByFolderId(SysFileInfoFolderIdMapperBO mapperBO);

	List<SysFileInfo> selectByFolderId(SysFileInfoFolderIdMapperBO mapperBO);

	List<SysFileInfo> selectByFolderIdList(SysFileInfoFolderIdListMapperBO mapperBO);


	List<SysFileInfo> selectByFileSuffix(SysFileInfoFileSuffixMapperBO mapperBO);

	List<SysFileInfo> selectByFileStorageName(SysFileInfoFileStorageNameMapperBO mapperBO);

	List<SysFileInfo> selectByFileShowName(SysFileInfoFileShowNameMapperBO mapperBO);


	List<SysFileInfo> selectByFileSuffixList(SysFileInfoFileSuffixListMapperBO mapperBO);

	List<SysFileInfo> selectByFileStorageNameList(SysFileInfoFileStorageNameListMapperBO mapperBO);

	List<SysFileInfo> selectByFileShowNameList(SysFileInfoFileShowNameListMapperBO mapperBO);


	List<SysFileInfo> selectByIdList(IdListMapperBO mapperBO);

	List<SysFileInfo> selectByPageQueryMapperBo(SysFileInfoPageQueryMapperBO mapperBO);

	List<SysFileInfo> selectByStateEnum(BaseQueryMapperBO mapperBO);

	List<SysFileInfo> selectByDeleteEnum(BaseQueryMapperBO mapperBO);

	List<SysFileInfo> selectByDeleteEnumAndStateEnum(BaseQueryMapperBO mapperBO);

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<SysFileInfo> list);

	int updateStateEnumByIdList(BatchUpdateStateMapperBO mapperBO);

	int updateDeleteEnumByIdList(BatchDeleteMapperBO mapperBO);

	int updateDeleteEnumByFolderIdList(SysFileInfoFolderIdListToDeleteMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
