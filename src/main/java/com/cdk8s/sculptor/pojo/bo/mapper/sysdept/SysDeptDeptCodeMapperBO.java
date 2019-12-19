package com.cdk8s.sculptor.pojo.bo.mapper.sysdept;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class SysDeptDeptCodeMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private String deptCode;
	private List<String> deptCodeList;

	public SysDeptDeptCodeMapperBO(String deptCode) {
		this.deptCode = deptCode;
	}

	public SysDeptDeptCodeMapperBO(List<String> deptCodeList) {
		this.deptCodeList = deptCodeList;
	}
}
