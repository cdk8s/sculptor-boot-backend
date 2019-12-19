package com.cdk8s.sculptor.pojo.bo.mapper.sysloginlog;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class SysLoginLogTokenMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private String token;
	private List<String> tokenList;

	public SysLoginLogTokenMapperBO(String token) {
		this.token = token;
	}

	public SysLoginLogTokenMapperBO(List<String> tokenList) {
		this.tokenList = tokenList;
	}
}
