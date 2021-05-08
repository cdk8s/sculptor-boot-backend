/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysUserInfoUserIdListRequestParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.sysuserinfo;

import com.cdk8s.sculptor.enums.DeleteEnum;
import com.cdk8s.sculptor.pojo.dto.param.bases.PageParam;
import com.cdk8s.sculptor.pojo.dto.param.bases.BaseQueryParam;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class SysUserInfoUserIdListRequestParam extends BaseQueryParam {

	private static final long serialVersionUID = -1L;

	@NotEmpty(message = "userIdList 不能为空")
	@Size(min = 1, message = "userIdList 至少需要一个元素")
	private List<Long> userIdList;

}
