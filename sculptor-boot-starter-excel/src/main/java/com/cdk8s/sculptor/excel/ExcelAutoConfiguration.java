/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：ExcelAutoConfiguration.java
 * 项目名称：sculptor-boot-starter-excel
 * 项目描述：sculptor-boot-starter-excel
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor.excel;

import com.alibaba.excel.ExcelWriter;
import com.cdk8s.sculptor.excel.service.ExcelDataService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@ConditionalOnClass({ExcelWriter.class})
public class ExcelAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean(ExcelDataService.class)
	public ExcelDataService excelDataService() {
		return new ExcelDataService();
	}
}
