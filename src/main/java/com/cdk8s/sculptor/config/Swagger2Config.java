package com.cdk8s.sculptor.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Date;


@Configuration
@EnableSwagger2
@ConditionalOnProperty(name = "custom.properties.init.swagger-enabled", havingValue = "true", matchIfMissing = false)
public class Swagger2Config {

	@Bean
	public Docket controllerApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("CDK8S Sculptor Boot Controller Api")
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.cdk8s.sculptor.controller"))
				.paths(PathSelectors.any())
				.build()
				.pathMapping("/")
				.directModelSubstitute(LocalDate.class, Long.class)
				.directModelSubstitute(ZonedDateTime.class, Long.class)
				.directModelSubstitute(LocalDateTime.class, Long.class)
				.directModelSubstitute(LocalDateTime.class, Long.class)
				.directModelSubstitute(Date.class, Long.class)
				.genericModelSubstitutes(ResponseEntity.class);
	}


	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("CDK8S Sculptor Boot API")
				.description("CDK8S Sculptor Boot API")
				.termsOfServiceUrl("http://localhost:8080/")
				.version("1.0.0")
				.build();
	}

}
