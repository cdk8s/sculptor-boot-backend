package com.cdk8s.sculptor.pojo.bo.service.sysrole;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SysRoleUpdateServiceBO implements Serializable {

	private static final long serialVersionUID = -1L;

	private Long id;
	private String roleName;
	private String roleCode;
	private Integer ranking;
	private String description;
	private Integer stateEnum;
	private Long updateDate;
	private Long updateUserId;

}
