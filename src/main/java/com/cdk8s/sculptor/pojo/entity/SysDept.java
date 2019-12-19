package com.cdk8s.sculptor.pojo.entity;

import com.cdk8s.sculptor.enums.BooleanEnum;
import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Transient;
import java.util.List;

@Setter
@Getter
@ToString(callSuper = true)
public class SysDept extends BaseEntity {

	private static final long serialVersionUID = -1L;

	private String deptName;
	private String deptCode;
	private Long parentId;
	private String parentIds;
	private Long leaderUserId;
	private String telephone;
	private String mobilePhone;
	private String deptFax;
	private String deptAddress;
	private Integer ranking;
	private String description;

	private Integer stateEnum;

	private Integer deleteEnum;
	private Long deleteDate;
	private Long deleteUserId;

	private Long createDate;
	private Long createUserId;

	private Long updateDate;
	private Long updateUserId;

	// ==============非 entity 属性 start==============

	@Transient
	private Integer boolParentEnum = BooleanEnum.NO.getCode();

	@Transient
	private List<SysDept> children;

	// ==============非 entity 属性 end==============

}

