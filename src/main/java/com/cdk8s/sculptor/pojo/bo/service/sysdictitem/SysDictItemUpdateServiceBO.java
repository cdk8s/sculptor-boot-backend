package com.cdk8s.sculptor.pojo.bo.service.sysdictitem;

import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class SysDictItemUpdateServiceBO implements Serializable {

	private static final long serialVersionUID = -1L;

	private Long id;
	private String itemName;
	private String itemCode;
	private String itemValue;
	private Integer dictValueTypeEnum;
	private Integer ranking;
	private String description;
	private Integer stateEnum;
	private Long updateDate;
	private Long updateUserId;

}
