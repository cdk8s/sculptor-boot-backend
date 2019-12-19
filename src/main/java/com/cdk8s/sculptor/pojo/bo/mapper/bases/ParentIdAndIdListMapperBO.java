package com.cdk8s.sculptor.pojo.bo.mapper.bases;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Setter
@Getter
@ToString(callSuper = true)
public class ParentIdAndIdListMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private Long parentId;
	private List<Long> parentIdList;
	private List<Long> idList;

	public ParentIdAndIdListMapperBO(Long parentId, List<Long> idList) {
		this.parentId = parentId;
		this.idList = idList;
	}

	public ParentIdAndIdListMapperBO(List<Long> parentIdList, List<Long> idList) {
		this.parentIdList = parentIdList;
		this.idList = idList;
	}
}
