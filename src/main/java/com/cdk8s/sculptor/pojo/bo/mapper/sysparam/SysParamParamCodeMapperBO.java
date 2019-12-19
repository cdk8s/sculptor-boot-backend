package com.cdk8s.sculptor.pojo.bo.mapper.sysparam;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class SysParamParamCodeMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private String paramCode;
	private List<String> paramCodeList;

	public SysParamParamCodeMapperBO(String paramCode) {
		this.paramCode = paramCode;
	}

	public SysParamParamCodeMapperBO(List<String> paramCodeList) {
		this.paramCodeList = paramCodeList;
	}
}
