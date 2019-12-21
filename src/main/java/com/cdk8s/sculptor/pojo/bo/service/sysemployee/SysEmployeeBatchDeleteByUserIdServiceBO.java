package com.cdk8s.sculptor.pojo.bo.service.sysemployee;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class SysEmployeeBatchDeleteByUserIdServiceBO implements Serializable {

	private static final long serialVersionUID = -1L;

	private Long userId;
	private List<Long> userIdList;
	private Long updateDate;
	private Long updateUserId;
	private Long deleteDate;
	private Long deleteUserId;

	public SysEmployeeBatchDeleteByUserIdServiceBO(Long userId) {
		this.userId = userId;
	}

	public SysEmployeeBatchDeleteByUserIdServiceBO(List<Long> userIdList) {
		this.userIdList = userIdList;
	}
}
