package com.cdk8s.sculptor.pojo.bo.mapper.reldeptuser;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class RelDeptUserUserIdMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private Long userId;
	private List<Long> userIdList;

	public RelDeptUserUserIdMapperBO(Long userId) {
		this.userId = userId;
	}

	public RelDeptUserUserIdMapperBO(List<Long> userIdList) {
		this.userIdList = userIdList;
	}
}
