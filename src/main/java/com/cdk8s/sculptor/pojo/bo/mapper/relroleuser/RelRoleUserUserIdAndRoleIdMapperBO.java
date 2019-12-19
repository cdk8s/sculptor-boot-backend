package com.cdk8s.sculptor.pojo.bo.mapper.relroleuser;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class RelRoleUserUserIdAndRoleIdMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private Long userId;
	private Long roleId;

	public RelRoleUserUserIdAndRoleIdMapperBO(Long userId, Long roleId) {
		this.userId = userId;
		this.roleId = roleId;
	}
}
