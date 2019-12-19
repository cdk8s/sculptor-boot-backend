package com.cdk8s.sculptor.init;

import com.cdk8s.sculptor.util.SpringUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Slf4j
@Order(20)
@Component
public class ApplicationEchoBeanInitRunner implements ApplicationRunner {

	@SneakyThrows
	@Override
	public void run(ApplicationArguments args) {

		ApplicationContext applicationContext = SpringUtil.getApplicationContext();
		if (applicationContext.getParent() == null) {
			log.info("=================================Spring 容器中的 Bean start=================================");
			for (String instanceBeanName : applicationContext.getBeanDefinitionNames()) {
				log.info("=================================Bean={}, 单例={}", instanceBeanName, applicationContext.isSingleton(instanceBeanName));
			}
			log.info("=================================Spring 容器中的 Bean end=================================");

		}
	}
}
