/*
 * Copyright © 2019-2021 CDK8S (cdk8s@qq.com)
 * All rights reserved.
 * 文件名称：Application.java
 * 项目名称：sculptor-boot-biz
 * 项目描述：sculptor-boot-biz
 * 版权说明：本软件属CDK8S所有
 */

package com.cdk8s.sculptor;

import com.cdk8s.tkey.client.rest.EnableTkeySso;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTkeySso
@EnableTransactionManagement
@EnableScheduling
@tk.mybatis.spring.annotation.MapperScan(basePackages = {"com.cdk8s.sculptor.mapper"})
@EnableRetry
@ServletComponentScan
@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {

	@Value("${server.port:9091}")
	private String serverPort;

	@Value("${server.servlet.context-path:/sculptor-boot-backend}")
	private String serverContextPath;

	@Value("${management.server.servlet.context-path:/sculptor-boot-backend-actuator}")
	private String managementContextPath;

	@Value("${management.server.port:19091}")
	private String managementPort;

	@Value("${spring.h2.console.path:/h2-console}")
	private String h2ConsolePath;

	//=================================================================================

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... strings) {
		log.info("=================================Application Startup Success=================================");
		log.info("index >> http://sculptor.cdk8s.com:{}{}", serverPort, serverContextPath);
		log.info("H2 Console >> http://sculptor.cdk8s.com:{}{}{}", serverPort, serverContextPath, h2ConsolePath);
		log.info("actuator-health >> http://sculptor.cdk8s.com:{}{}/actuator/health", managementPort, managementContextPath);
		log.info("=================================Application Startup Success=================================");
	}


}

