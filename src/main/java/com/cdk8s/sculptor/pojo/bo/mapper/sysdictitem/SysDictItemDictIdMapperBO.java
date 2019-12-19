package com.cdk8s.sculptor.pojo.bo.mapper.sysdictitem;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class SysDictItemDictIdMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private Long dictId;
	private List<Long> dictIdList;

	public SysDictItemDictIdMapperBO(Long dictId) {
		this.dictId = dictId;
	}

	public SysDictItemDictIdMapperBO(List<Long> dictIdList) {
		this.dictIdList = dictIdList;
	}
}
