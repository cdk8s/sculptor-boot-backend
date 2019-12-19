package com.cdk8s.sculptor.pojo.bo.mapper.sysrole;

import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.pojo.dto.param.bases.PageParam;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SysRolePageQueryMapperBO extends PageParam {

	private static final long serialVersionUID = -1L;

	private Long id;
	private String roleName;
	private String roleCode;
	private Integer ranking;
	private String description;
	private Integer stateEnum;
	private Long createUserId;

	private Integer deleteEnum = DeleteEnum.NOT_DELETED.getCode();
}
