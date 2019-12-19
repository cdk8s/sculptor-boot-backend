package com.cdk8s.sculptor.pojo.bo.mapper.sysloginlog;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class SysLoginLogUsernameMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private String username;
	private List<String> usernameList;

	public SysLoginLogUsernameMapperBO(String username) {
		this.username = username;
	}

	public SysLoginLogUsernameMapperBO(List<String> usernameList) {
		this.usernameList = usernameList;
	}
}
