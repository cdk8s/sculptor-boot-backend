package com.cdk8s.sculptor.pojo.bo.mapper.sysemployee;

import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.pojo.dto.param.bases.PageParam;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SysEmployeePageQueryMapperBO extends PageParam {

	private static final long serialVersionUID = -1L;

	private Long id;
	private Long userId;
	private String workCardId;
	private String jobPosition;
	private Long createUserId;

	private Integer deleteEnum = DeleteEnum.NOT_DELETED.getCode();

	// ==============非 entity 属性 start==============

	private String username;
	private String realName;

	// ==============非 entity 属性 end==============
}
