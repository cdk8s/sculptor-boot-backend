package com.cdk8s.sculptor.pojo.bo.mapper.bases;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Setter
@Getter
@ToString(callSuper = true)
public class ParentIdMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private Long parentId;
	private List<Long> parentIdList;

	public ParentIdMapperBO(Long parentId) {
		this.parentId = parentId;
	}

	public ParentIdMapperBO(List<Long> parentIdList) {
		this.parentIdList = parentIdList;
	}
}
