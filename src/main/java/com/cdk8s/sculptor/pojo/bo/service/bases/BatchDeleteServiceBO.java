package com.cdk8s.sculptor.pojo.bo.service.bases;

import com.cdk8s.sculptor.enums.DeleteEnum;
import lombok.*;

import java.io.Serializable;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class BatchDeleteServiceBO implements Serializable {

	private static final long serialVersionUID = -1L;

	// 必须有的字段
	private List<Long> idList;

	// 非必须字段
	private Integer deleteEnum = DeleteEnum.DELETED.getCode();
	private Long deleteDate;
	private Long deleteUserId;

	private Long updateDate;
	private Long updateUserId;

}
