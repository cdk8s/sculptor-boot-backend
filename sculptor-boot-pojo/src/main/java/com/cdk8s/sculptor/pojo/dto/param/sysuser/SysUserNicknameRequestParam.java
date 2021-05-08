/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysUserNicknameRequestParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.sysuser;

import com.cdk8s.sculptor.pojo.dto.param.bases.BaseQueryParam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class SysUserNicknameRequestParam extends BaseQueryParam {

	private static final long serialVersionUID = -1L;

	@Length(max = 100, message = "昵称长度不正确")
	@SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
	private String nickname;


}
