package com.cdk8s.sculptor.pojo.bo.mapper.syseventlog;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class SysEventLogUsernameMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private String username;
	private List<String> usernameList;

	public SysEventLogUsernameMapperBO(String username) {
		this.username = username;
	}

	public SysEventLogUsernameMapperBO(List<String> usernameList) {
		this.usernameList = usernameList;
	}
}
