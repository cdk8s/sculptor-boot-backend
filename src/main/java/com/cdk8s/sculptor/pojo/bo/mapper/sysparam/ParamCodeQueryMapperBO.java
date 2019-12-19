package com.cdk8s.sculptor.pojo.bo.mapper.sysparam;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Setter
@Getter
@ToString(callSuper = true)
public class ParamCodeQueryMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private String paramCode;

	public ParamCodeQueryMapperBO(String paramCode) {
		this.paramCode = paramCode;
	}
}
