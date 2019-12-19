package com.cdk8s.sculptor.pojo.bo.mapper.sysparam;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class SysParamTypeIdMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private Long typeId;
	private List<Long> typeIdList;

	public SysParamTypeIdMapperBO(Long typeId) {
		this.typeId = typeId;
	}

	public SysParamTypeIdMapperBO(List<Long> typeIdList) {
		this.typeIdList = typeIdList;
	}
}
