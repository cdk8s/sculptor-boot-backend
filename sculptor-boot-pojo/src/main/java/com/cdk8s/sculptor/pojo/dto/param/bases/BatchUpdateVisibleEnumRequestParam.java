/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：BatchUpdateVisibleEnumRequestParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.bases;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatchUpdateVisibleEnumRequestParam extends BaseUpdateParam {

	private static final long serialVersionUID = -1L;

	@NotEmpty(message = "idList 不能为空")
	@Size(min = 1, message = "idList 至少需要一个元素")
	private List<Long> idList;

	@NotNull(message = "枚举值不能为空")
	@Range(min = 1, max = 2, message = "枚举值数值不正确")
	private Integer visibleEnum;

}
