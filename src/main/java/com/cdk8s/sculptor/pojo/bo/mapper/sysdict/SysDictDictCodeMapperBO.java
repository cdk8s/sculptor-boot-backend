package com.cdk8s.sculptor.pojo.bo.mapper.sysdict;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class SysDictDictCodeMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private String dictCode;
	private List<String> dictCodeList;

	public SysDictDictCodeMapperBO(String dictCode) {
		this.dictCode = dictCode;
	}

	public SysDictDictCodeMapperBO(List<String> dictCodeList) {
		this.dictCodeList = dictCodeList;
	}
}
