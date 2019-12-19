package com.cdk8s.sculptor.pojo.dto.param.bases;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@ApiModel(value = "PageParam")
@Setter
@Getter
@ToString(callSuper = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageParam implements Serializable {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty("当前页")
	@NotNull(message = "当前页为空")
	@Range(min = 1, max = 1000, message = "当前页最小值 1，最大值 1000")
	private Integer pageNum;

	@ApiModelProperty("每页数量")
	@NotNull(message = "每页数量为空")
	@Range(min = 1, max = 100, message = "每页数量最大值 100")
	private Integer pageSize;

	@ApiModelProperty("创建时间的查询开始时间")
	@Range(min = 1, message = "创建时间的查询开始时间最小值 1")
	private Long createDateStartDate;

	@ApiModelProperty("创建时间的查询结束时间")
	@Range(min = 1, message = "创建时间的查询结束时间最小值 1")
	private Long createDateEndDate;

	@ApiModelProperty("更新时间的查询开始时间")
	@Range(min = 1, message = "更新时间的查询开始时间最小值 1")
	private Long updateDateStartDate;

	@ApiModelProperty("更新时间的查询结束时间")
	@Range(min = 1, message = "更新时间的查询结束时间最小值 1")
	private Long updateDateEndDate;


}
