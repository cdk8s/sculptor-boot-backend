package com.cdk8s.sculptor.pojo.bo.mapper.reldeptuser;

import com.cdk8s.sculptor.pojo.dto.param.bases.PageParam;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class RelDeptUserPageQueryMapperBO extends PageParam {

	private static final long serialVersionUID = -1L;

	private Long id;
	private Long deptId;
	private Long userId;
	private Long createUserId;


}
