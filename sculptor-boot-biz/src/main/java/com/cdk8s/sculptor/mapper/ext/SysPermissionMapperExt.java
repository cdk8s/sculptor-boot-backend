/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysPermissionMapperExt.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.mapper.ext;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.IdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.ParentIdAndIdListMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.ParentIdMapperBO;
import com.cdk8s.sculptor.pojo.entity.SysPermission;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysPermissionMapperExt {

	// =====================================查询业务 start=====================================

	List<SysPermission> selectByParentIdNotButton(ParentIdMapperBO mapperBO);

	List<SysPermission> selectByParentIdAndIdListNotButton(ParentIdAndIdListMapperBO mapperBO);

	List<SysPermission> selectByIdListNotButton(IdListMapperBO mapperBO);


	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================


	// =====================================操作业务 end=====================================

}
