package com.cdk8s.sculptor.pojo.bo.service.reldeptuser;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class RelDeptUserBatchCreateServiceBO implements Serializable {

	private static final long serialVersionUID = -1L;

	private Long createDate;
	private Long createUserId;

	private List<Long> deptIdList;

	private List<Long> userIdList;


}
