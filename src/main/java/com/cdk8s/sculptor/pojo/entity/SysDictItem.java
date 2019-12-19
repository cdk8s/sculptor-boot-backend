package com.cdk8s.sculptor.pojo.entity;

import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class SysDictItem extends BaseEntity {

	private static final long serialVersionUID = -1L;

	private Long dictId;
	private String dictCode;
	private String itemName;
	private String itemCode;
	private String itemValue;
	private Integer dictValueTypeEnum;
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


}

