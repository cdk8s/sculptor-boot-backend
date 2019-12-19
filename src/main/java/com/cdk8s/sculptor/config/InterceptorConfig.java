package com.cdk8s.sculptor.config;


import com.cdk8s.sculptor.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


	@Bean
	public LoginInterceptor loginCheckInterceptor() {
		return new LoginInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(loginCheckInterceptor())
				.addPathPatterns("/**")
				.excludePathPatterns("/", "/error")
				.excludePathPatterns("/swagger-ui.html/**", "/webjars/**", "/swagger-resources/**", "/v2/**")
				.excludePathPatterns("/doc.html/**", "/webjars/**")
				.excludePathPatterns("/codeCallback", "/logoutSuccess", "login/**", "/logout/**")
				.excludePathPatterns("/oauth/**")
				.excludePathPatterns("/druid/**")
				.excludePathPatterns("/validate/code/**")
				.excludePathPatterns("/api/sysCommon/open/**")
				.excludePathPatterns("/webjars/**")
				.excludePathPatterns("/fonts/**")
				.excludePathPatterns("/images/**")
				.excludePathPatterns("/js/**")
				.excludePathPatterns("/css/**");
	}


}
