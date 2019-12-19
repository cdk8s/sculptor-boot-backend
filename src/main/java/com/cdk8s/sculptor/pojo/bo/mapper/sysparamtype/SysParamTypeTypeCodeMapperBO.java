package com.cdk8s.sculptor.pojo.bo.mapper.sysparamtype;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class SysParamTypeTypeCodeMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private String typeCode;
	private List<String> typeCodeList;

	public SysParamTypeTypeCodeMapperBO(String typeCode) {
		this.typeCode = typeCode;
	}

	public SysParamTypeTypeCodeMapperBO(List<String> typeCodeList) {
		this.typeCodeList = typeCodeList;
	}
}
