package com.cdk8s.sculptor.pojo.bo.mapper.bases;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@ToString(callSuper = true)
public class IdListMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private List<Long> idList;

}
