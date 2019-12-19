package com.cdk8s.sculptor.pojo.dto.response.sysemployee;

import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ApiModel(value = "SysEmployeeResponseDTO")
@Setter
@Getter
@ToString(callSuper = true)
public class SysEmployeeResponseDTO extends BaseEntity {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "用户ID")
	private Long userId;

	@ApiModelProperty(value = "工号")
	private String workCardId;

	@ApiModelProperty(value = "职位")
	private String jobPosition;

	@ApiModelProperty(value = "创建时间")
	private Long createDate;

	@ApiModelProperty(value = "创建人ID")
	private Long createUserId;

	@ApiModelProperty(value = "创建人")
	private String createUsername;

	@ApiModelProperty(value = "更新时间")
	private Long updateDate;

	@ApiModelProperty(value = "更新人ID")
	private Long updateUserId;

	@ApiModelProperty(value = "更新人")
	private String updateUsername;


	// ==============非 entity 属性 start==============

	private String username;
	private String realName;

	// ==============非 entity 属性 end==============

}
