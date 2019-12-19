package com.cdk8s.sculptor.pojo.bo.mapper.sysemployee;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class SysEmployeeUserIdMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private Long userId;
	private List<Long> userIdList;

	public SysEmployeeUserIdMapperBO(Long userId) {
		this.userId = userId;
	}

	public SysEmployeeUserIdMapperBO(List<Long> userIdList) {
		this.userIdList = userIdList;
	}
}
