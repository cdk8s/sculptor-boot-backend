package com.cdk8s.sculptor.pojo.bo.mapper.sysemployee;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class SysEmployeeBatchDeleteByUserIdMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private Long userId;
	private List<Long> userIdList;
	private Long updateDate;
	private Long updateUserId;
	private Long deleteDate;
	private Long deleteUserId;

	public SysEmployeeBatchDeleteByUserIdMapperBO(Long userId) {
		this.userId = userId;
	}

	public SysEmployeeBatchDeleteByUserIdMapperBO(List<Long> userIdList) {
		this.userIdList = userIdList;
	}
}
