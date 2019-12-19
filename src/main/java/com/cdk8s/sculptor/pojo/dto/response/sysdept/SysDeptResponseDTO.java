package com.cdk8s.sculptor.pojo.dto.response.sysdept;

import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ApiModel(value = "SysDeptResponseDTO")
@Setter
@Getter
@ToString(callSuper = true)
public class SysDeptResponseDTO extends BaseEntity {

	private static final long serialVersionUID = -1L;

	@ApiModelProperty(value = "部门名称")
	private String deptName;

	@ApiModelProperty(value = "部门编码")
	private String deptCode;

	@ApiModelProperty(value = "父ID")
	private Long parentId;

	@ApiModelProperty(value = "父ID集")
	private String parentIds;

	@ApiModelProperty(value = "部门领导用户ID")
	private Long leaderUserId;

	@ApiModelProperty(value = "部门领导真实姓名")
	private String leaderRealName;

	@ApiModelProperty(value = "固话")
	private String telephone;

	@ApiModelProperty(value = "手机号")
	private String mobilePhone;

	@ApiModelProperty(value = "传真")
	private String deptFax;

	@ApiModelProperty(value = "地址")
	private String deptAddress;

	@ApiModelProperty(value = "排序")
	private Integer ranking;

	@ApiModelProperty(value = "描述")
	private String description;

	@ApiModelProperty(value = "启用状态")
	private Integer stateEnum;

	@ApiModelProperty(value = "启用状态")
	private String stateEnumString;


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

	@ApiModelProperty(value = "是否是父节点")
	private Integer boolParentEnum;

	@ApiModelProperty(value = "子节点列表")
	private List<SysDeptResponseDTO> children;

}
