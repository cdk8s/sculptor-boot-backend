package com.cdk8s.sculptor.pojo.bo.service.bases;

import lombok.*;

import java.io.Serializable;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class BatchUpdateStateServiceBO implements Serializable {

	private static final long serialVersionUID = -1L;

	// 必须字段
	private List<Long> idList;
	private Integer stateEnum;

	private Long updateDate;
	private Long updateUserId;

}
