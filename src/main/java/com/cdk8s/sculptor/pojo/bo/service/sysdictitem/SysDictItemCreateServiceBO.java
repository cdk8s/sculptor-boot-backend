package com.cdk8s.sculptor.pojo.bo.service.sysdictitem;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SysDictItemCreateServiceBO implements Serializable {

	private static final long serialVersionUID = -1L;

	private Long id;
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
	private Long createDate;
	private Long createUserId;
	private Long updateDate;
	private Long updateUserId;
	private Long deleteDate;
	private Long deleteUserId;

}
