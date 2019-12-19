package com.cdk8s.sculptor.pojo.bo.mapper.sysuser;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class SysUserUsernameMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private String username;
	private List<String> usernameList;

	public SysUserUsernameMapperBO(String username) {
		this.username = username;
	}

	public SysUserUsernameMapperBO(List<String> usernameList) {
		this.usernameList = usernameList;
	}
}
