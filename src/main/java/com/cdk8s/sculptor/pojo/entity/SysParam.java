package com.cdk8s.sculptor.pojo.entity;

import com.cdk8s.sculptor.pojo.entity.bases.BaseEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString(callSuper = true)
public class SysParam extends BaseEntity {

	private static final long serialVersionUID = -1L;

	private Long typeId;
	private String typeCode;
	private String paramName;
	private String paramCode;
	private String paramValue;
	private Integer ranking;
	private String description;
	private Integer paramValueTypeEnum;

	private Integer stateEnum;

	private Integer deleteEnum;
	private Long deleteDate;
	private Long deleteUserId;

	private Long createDate;
	private Long createUserId;

	private Long updateDate;
	private Long updateUserId;


}

