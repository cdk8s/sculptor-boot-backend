/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：AppIndexRequestParam.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.multiapi.pojo.param;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AppIndexRequestParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@NotNull(message = "ID不能为空")
	@Range(min = 1, message = "ID数值不正确")
	private Long id;

	@NotNull(message = "类型不能为空")
	@Range(min = 1, max = 2, message = "类型数值不正确")
	private Integer orderType;
}
