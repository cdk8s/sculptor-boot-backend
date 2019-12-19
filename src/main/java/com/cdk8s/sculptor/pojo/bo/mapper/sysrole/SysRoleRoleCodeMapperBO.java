package com.cdk8s.sculptor.pojo.bo.mapper.sysrole;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class SysRoleRoleCodeMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private String roleCode;
	private List<String> roleCodeList;

	public SysRoleRoleCodeMapperBO(String roleCode) {
		this.roleCode = roleCode;
	}

	public SysRoleRoleCodeMapperBO(List<String> roleCodeList) {
		this.roleCodeList = roleCodeList;
	}
}
