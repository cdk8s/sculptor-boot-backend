package com.cdk8s.sculptor.pojo.bo.mapper.bases;


import com.cdk8s.sculptor.enums.DeleteEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;


@Setter
@Getter
@ToString(callSuper = true)
public class BaseQueryMapperBO implements Serializable {

	private static final long serialVersionUID = -1L;

	private Integer deleteEnum = DeleteEnum.NOT_DELETED.getCode();
	private Integer stateEnum;

}
