package com.cdk8s.sculptor.pojo.bo.service.relroleuser;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class RelRoleUserCreateServiceBO implements Serializable {

	private static final long serialVersionUID = -1L;

	private Long id;
	private Long roleId;
	private Long userId;
	private Long createDate;
	private Long createUserId;

}
