package com.cdk8s.sculptor.pojo.bo.service.sysparamtype;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SysParamTypeCreateServiceBO implements Serializable {

	private static final long serialVersionUID = -1L;

	private Long id;
	private String typeName;
	private String typeCode;
	private Integer ranking;
	private String description;
	private Integer stateEnum;
	private Integer deleteEnum;
	private Long createDate;
	private Long createUserId;
	private Long updateDate;
	private Long updateUserId;
	private Long deleteDate;
	private Long deleteUserId;

}
