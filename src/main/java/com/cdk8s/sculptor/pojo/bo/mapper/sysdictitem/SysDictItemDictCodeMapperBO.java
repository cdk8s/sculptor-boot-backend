package com.cdk8s.sculptor.pojo.bo.mapper.sysdictitem;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class SysDictItemDictCodeMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private String dictCode;
	private List<String> dictCodeList;

	public SysDictItemDictCodeMapperBO(String dictCode) {
		this.dictCode = dictCode;
	}

	public SysDictItemDictCodeMapperBO(List<String> dictCodeList) {
		this.dictCodeList = dictCodeList;
	}
}
