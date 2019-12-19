package com.cdk8s.sculptor.mapper;

import com.cdk8s.sculptor.config.CustomBaseMapper;
import com.cdk8s.sculptor.pojo.bo.mapper.bases.*;
import com.cdk8s.sculptor.pojo.bo.mapper.sysdictitem.SysDictItemDictCodeMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysdictitem.SysDictItemDictIdMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysdictitem.SysDictItemItemCodeMapperBO;
import com.cdk8s.sculptor.pojo.bo.mapper.sysdictitem.SysDictItemPageQueryMapperBO;
import com.cdk8s.sculptor.pojo.entity.SysDictItem;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface SysDictItemMapper extends CustomBaseMapper<SysDictItem> {

	// =====================================查询业务 start=====================================

	SysDictItem selectById(IdMapperBO mapperBO);

	SysDictItem selectOneByDictId(SysDictItemDictIdMapperBO mapperBO);

	List<SysDictItem> selectByDictId(SysDictItemDictIdMapperBO mapperBO);

	List<SysDictItem> selectByDictIdList(SysDictItemDictIdMapperBO mapperBO);

	List<SysDictItem> selectByDictCode(SysDictItemDictCodeMapperBO mapperBO);

	List<SysDictItem> selectByItemCode(SysDictItemItemCodeMapperBO mapperBO);


	List<SysDictItem> selectByDictCodeList(SysDictItemDictCodeMapperBO mapperBO);

	List<SysDictItem> selectByItemCodeList(SysDictItemItemCodeMapperBO mapperBO);


	List<SysDictItem> selectByIdList(IdListMapperBO mapperBO);

	List<SysDictItem> selectByPageQueryMapperBo(SysDictItemPageQueryMapperBO mapperBO);

	List<SysDictItem> selectByStateEnum(BaseQueryMapperBO mapperBO);

	List<SysDictItem> selectByDeleteEnum(BaseQueryMapperBO mapperBO);

	List<SysDictItem> selectByDeleteEnumAndStateEnum(BaseQueryMapperBO mapperBO);

	// =====================================查询业务 end=====================================
	// =====================================操作业务 start=====================================

	int batchInsertList(@Param("list") List<SysDictItem> list);

	int updateStateEnumByIdList(BatchUpdateStateMapperBO mapperBO);

	int updateDeleteEnumByIdList(BatchDeleteMapperBO mapperBO);

	int updateDeleteEnumByDictIdList(SysDictItemDictIdMapperBO mapperBO);


	// =====================================操作业务 end=====================================

}
