package com.cdk8s.sculptor.pojo.entity.bases;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Id;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class BaseEntity implements Serializable {

	private static final long serialVersionUID = -1L;

	@Id
	protected Long id;

}
