package com.cdk8s.sculptor.pojo.entity;

import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Transient;

@Setter
@Getter
@ToString(callSuper = true)
public class SysEmployee extends BaseEntity {

	private static final long serialVersionUID = -1L;

	private Long userId;
	private String workCardId;
	private String jobPosition;


	private Integer deleteEnum;
	private Long deleteDate;
	private Long deleteUserId;

	private Long createDate;
	private Long createUserId;

	private Long updateDate;
	private Long updateUserId;

	// ==============非 entity 属性 start==============

	@Transient
	private String username;

	@Transient
	private String realName;

	// ==============非 entity 属性 end==============


}

