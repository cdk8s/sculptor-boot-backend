package com.cdk8s.sculptor.pojo.bo.mapper.reldeptuser;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class RelDeptUserDeptIdMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private Long deptId;
	private List<Long> deptIdList;

	public RelDeptUserDeptIdMapperBO(Long deptId) {
		this.deptId = deptId;
	}

	public RelDeptUserDeptIdMapperBO(List<Long> deptIdList) {
		this.deptIdList = deptIdList;
	}
}
