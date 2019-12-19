package com.cdk8s.sculptor.service.oauth;

import com.cdk8s.sculptor.constant.GlobalConstant;
import com.cdk8s.sculptor.enums.ResponseProduceTypeEnum;
import com.cdk8s.sculptor.enums.StateEnum;
import com.cdk8s.sculptor.exception.OauthApiException;
import com.cdk8s.sculptor.pojo.dto.response.oauth.OauthUserAttribute;
import com.cdk8s.sculptor.pojo.entity.SysUser;
import com.cdk8s.sculptor.service.SysUserService;
import com.cdk8s.sculptor.util.StringUtil;
import com.cdk8s.sculptor.util.code.Md5Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class OauthLoginApiService {

	@Autowired
	private SysUserService sysUserService;

	//=====================================业务处理 start=====================================

	public OauthUserAttribute requestLoginApi(String username, String passsword) {
		username = StringUtil.trim(username);
		SysUser sysUser = sysUserService.findOneByUsername(username);
		if (null == sysUser) {
			throw new OauthApiException("用户不存在", ResponseProduceTypeEnum.HTML, GlobalConstant.DEFAULT_LOGIN_PAGE_PATH);
		}

		if (sysUser.getStateEnum().equals(StateEnum.DISABLE.getCode())) {
			throw new OauthApiException("该用户已被停用", ResponseProduceTypeEnum.HTML, GlobalConstant.DEFAULT_LOGIN_PAGE_PATH);
		}

		String passwordByMD5 = Md5Util.md5ByGuava(passsword + sysUser.getPasswordSalt());

		if (StringUtil.notEquals(sysUser.getUserPassword(), passwordByMD5)) {
			throw new OauthApiException("用户名或密码错误", ResponseProduceTypeEnum.HTML, GlobalConstant.DEFAULT_LOGIN_PAGE_PATH);
		}

		OauthUserAttribute oauthUserAttribute = new OauthUserAttribute();
		oauthUserAttribute.setEmail(sysUser.getUserEmail());
		oauthUserAttribute.setUserId(String.valueOf(sysUser.getId()));
		oauthUserAttribute.setUsername(sysUser.getUsername());
		return oauthUserAttribute;
	}

	//=====================================业务处理  end=====================================

	//=====================================私有方法 start=====================================


	//=====================================私有方法  end=====================================

}
