package com.cdk8s.sculptor.pojo.bo.mapper.bases;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@AllArgsConstructor
@ToString(callSuper = true)
public class IdMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private Long id;

}
