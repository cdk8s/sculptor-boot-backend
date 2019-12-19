package com.cdk8s.sculptor.pojo.bo.mapper.sysuser;

import com.cdk8s.sculptor.pojo.bo.mapper.bases.BaseQueryMapperBO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class SysUserMobilePhoneMapperBO extends BaseQueryMapperBO {

	private static final long serialVersionUID = -1L;

	private String mobilePhone;
	private List<String> mobilePhoneList;

	public SysUserMobilePhoneMapperBO(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public SysUserMobilePhoneMapperBO(List<String> mobilePhoneList) {
		this.mobilePhoneList = mobilePhoneList;
	}
}
