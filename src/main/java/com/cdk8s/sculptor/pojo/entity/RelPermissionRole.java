package com.cdk8s.sculptor.pojo.entity;

import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class RelPermissionRole extends BaseEntity {

	private static final long serialVersionUID = -1L;

	private Long permissionId;
	private Long roleId;


	private Long createDate;
	private Long createUserId;


}

