package com.cdk8s.sculptor.pojo.bo.mapper.relpermissionrole;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class RelPermissionRoleRoleIdMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private Long roleId;
	private List<Long> roleIdList;

	public RelPermissionRoleRoleIdMapperBO(Long roleId) {
		this.roleId = roleId;
	}

	public RelPermissionRoleRoleIdMapperBO(List<Long> roleIdList) {
		this.roleIdList = roleIdList;
	}
}
