package com.cdk8s.sculptor;

import com.cdk8s.tkey.client.rest.EnableTkeySso;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTkeySso
@EnableTransactionManagement
@EnableScheduling
@tk.mybatis.spring.annotation.MapperScan(basePackages = {"com.cdk8s.sculptor.mapper"})
@EnableAsync
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

	@Value("${spring.h2.console.path:/abc}")
	private String h2ConsolePath;

	//=================================================================================

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... strings) {
		log.info("=================================Application Startup Success=================================");
		log.info("index >> http://sculptor.cdk8s.com:{}{}", serverPort, serverContextPath);
		log.info("login-page >> http://sculptor.cdk8s.com:{}{}/oauth/authorize?response_type=code&client_id=test_client_id_1&redirect_uri=http%3A%2F%2Flocalhost%3A8000%2FcodeCallback%3Fredirect_uri%3Dhttp%253A%252F%252Flocalhost%253A8000%252FTKeyClient", serverPort, serverContextPath);
		log.info("H2 Console >> http://sculptor.cdk8s.com:{}{}{}", serverPort, serverContextPath, h2ConsolePath);
		log.info("druid >> http://sculptor.cdk8s.com:{}{}/druid", serverPort, serverContextPath);
		log.info("swagger >> http://sculptor.cdk8s.com:{}{}/swagger-ui.html", serverPort, serverContextPath);
		log.info("knife4j >> http://sculptor.cdk8s.com:{}{}/doc.html", serverPort, serverContextPath);
		log.info("validate-code >> http://sculptor.cdk8s.com:{}{}/validate/code/imageCode/123456", serverPort, serverContextPath);
		log.info("actuator-health >> http://sculptor.cdk8s.com:{}{}/actuator/health", managementPort, managementContextPath);
		log.info("actuator-prometheus >> http://sculptor.cdk8s.com:{}{}/actuator/prometheus", managementPort, managementContextPath);
		log.info("=================================Application Startup Success=================================");
	}


}

