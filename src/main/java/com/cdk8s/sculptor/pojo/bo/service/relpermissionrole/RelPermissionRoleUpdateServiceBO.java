package com.cdk8s.sculptor.pojo.bo.service.relpermissionrole;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class RelPermissionRoleUpdateServiceBO implements Serializable {

	private static final long serialVersionUID = -1L;

	private Long id;
	private Long permissionId;
	private Long roleId;

}
