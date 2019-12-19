package com.cdk8s.sculptor.pojo.bo.mapper.syseventlog;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class SysEventLogUserIdMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private Long userId;
	private List<Long> userIdList;

	public SysEventLogUserIdMapperBO(Long userId) {
		this.userId = userId;
	}

	public SysEventLogUserIdMapperBO(List<Long> userIdList) {
		this.userIdList = userIdList;
	}
}
