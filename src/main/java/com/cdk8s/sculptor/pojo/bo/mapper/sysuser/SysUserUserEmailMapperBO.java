package com.cdk8s.sculptor.pojo.bo.mapper.sysuser;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class SysUserUserEmailMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private String userEmail;
	private List<String> userEmailList;

	public SysUserUserEmailMapperBO(String userEmail) {
		this.userEmail = userEmail;
	}

	public SysUserUserEmailMapperBO(List<String> userEmailList) {
		this.userEmailList = userEmailList;
	}
}
