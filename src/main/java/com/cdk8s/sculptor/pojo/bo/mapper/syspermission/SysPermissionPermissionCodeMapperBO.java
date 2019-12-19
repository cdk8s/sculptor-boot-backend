package com.cdk8s.sculptor.pojo.bo.mapper.syspermission;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class SysPermissionPermissionCodeMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private String permissionCode;
	private List<String> permissionCodeList;

	public SysPermissionPermissionCodeMapperBO(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public SysPermissionPermissionCodeMapperBO(List<String> permissionCodeList) {
		this.permissionCodeList = permissionCodeList;
	}
}
