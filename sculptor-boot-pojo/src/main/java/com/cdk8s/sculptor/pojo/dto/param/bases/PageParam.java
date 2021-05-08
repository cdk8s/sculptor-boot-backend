/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：PageParam.java
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

import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageParam extends BaseQueryParam {

	private static final long serialVersionUID = -1L;

	@Range(min = 1, max = 1000, message = "当前页最小值 1，最大值 1000")
	private Integer pageNum = 1;

	@Range(min = 1, max = 1000, message = "每页数量最大值 1000")
	private Integer pageSize = 10;

	@Range(min = 1, message = "创建时间的查询开始时间最小值 1")
	private Long createDateStartDate;

	@Range(min = 1, message = "创建时间的查询结束时间最小值 1")
	private Long createDateEndDate;

	@Range(min = 1, message = "更新时间的查询开始时间最小值 1")
	private Long updateDateStartDate;

	@Range(min = 1, message = "更新时间的查询结束时间最小值 1")
	private Long updateDateEndDate;

	private List<String> orderByList;

}
