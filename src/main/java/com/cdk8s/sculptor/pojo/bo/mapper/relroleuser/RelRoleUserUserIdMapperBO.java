package com.cdk8s.sculptor.pojo.bo.mapper.relroleuser;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class RelRoleUserUserIdMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private Long userId;
	private List<Long> userIdList;

	public RelRoleUserUserIdMapperBO(Long userId) {
		this.userId = userId;
	}

	public RelRoleUserUserIdMapperBO(List<Long> userIdList) {
		this.userIdList = userIdList;
	}
}
