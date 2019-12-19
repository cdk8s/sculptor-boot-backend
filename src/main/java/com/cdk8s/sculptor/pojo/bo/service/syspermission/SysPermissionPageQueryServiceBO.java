package com.cdk8s.sculptor.pojo.bo.service.syspermission;

import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.pojo.dto.param.bases.PageParam;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SysPermissionPageQueryServiceBO extends PageParam {

	private static final long serialVersionUID = -1L;

	private Long id;
	private String permissionName;
	private String permissionCode;
	private String permissionUrl;
	private Integer permissionTypeEnum;
	private Long parentId;
	private String parentIds;
	private String iconClass;
	private Integer visibleEnum;
	private Integer boolExtLinkEnum;
	private Integer boolNewTabEnum;
	private Integer ranking;
	private String description;
	private Integer stateEnum;
	private Long createUserId;


	private Integer deleteEnum = DeleteEnum.NOT_DELETED.getCode();
}
