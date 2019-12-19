package com.cdk8s.sculptor.pojo.bo.service.sysdept;

import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.pojo.dto.param.bases.PageParam;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SysDeptPageQueryServiceBO extends PageParam {

	private static final long serialVersionUID = -1L;

	private Long id;
	private String deptName;
	private String deptCode;
	private Long parentId;
	private String parentIds;
	private Long leaderUserId;
	private String telephone;
	private String mobilePhone;
	private String deptFax;
	private String deptAddress;
	private Integer ranking;
	private String description;
	private Integer stateEnum;
	private Long createUserId;


	private Integer deleteEnum = DeleteEnum.NOT_DELETED.getCode();
}
