package com.cdk8s.sculptor.pojo.entity;

import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class RelDeptUser extends BaseEntity {

	private static final long serialVersionUID = -1L;

	private Long deptId;
	private Long userId;


	private Long createDate;
	private Long createUserId;


}

