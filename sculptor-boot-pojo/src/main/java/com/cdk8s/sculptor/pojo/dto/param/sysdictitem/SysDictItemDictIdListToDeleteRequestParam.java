/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：SysDictItemDictIdListToDeleteRequestParam.java
 * 项目名称：sculptor-boot-pojo
 * 项目描述：简单类
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.pojo.dto.param.sysdictitem;

import com.cdk8s.sculptor.pojo.dto.param.bases.BaseDeleteParam;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.List;


@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class SysDictItemDictIdListToDeleteRequestParam extends BaseDeleteParam {

	private static final long serialVersionUID = -1L;

	@NotEmpty(message = "dictIdList 不能为空")
	@Size(min = 1, message = "dictIdList 至少需要一个元素")
	private List<Long> dictIdList;

}
