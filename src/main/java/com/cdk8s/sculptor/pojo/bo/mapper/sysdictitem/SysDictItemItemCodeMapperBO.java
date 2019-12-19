package com.cdk8s.sculptor.pojo.bo.mapper.sysdictitem;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class SysDictItemItemCodeMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private String itemCode;
	private List<String> itemCodeList;

	public SysDictItemItemCodeMapperBO(String itemCode) {
		this.itemCode = itemCode;
	}

	public SysDictItemItemCodeMapperBO(List<String> itemCodeList) {
		this.itemCodeList = itemCodeList;
	}
}
