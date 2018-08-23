package com.kurra.myRetail

import com.kurra.myRetail.exception.RestTemplateResponseErrorHandler
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.web.client.RestTemplate

import java.util.concurrent.Executor

@SpringBootApplication
@EnableAsync
class MyRetailApplication {

	static void main(String[] args) {
		SpringApplication.run MyRetailApplication, args
	}

	@Bean
	RestTemplate restTemplate(RestTemplateBuilder builder, RestTemplateResponseErrorHandler errorHandler) {
		return builder.errorHandler(errorHandler).build()
	}

	@Bean
	Executor asyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10)
		executor.setMaxPoolSize(20)
		executor.setQueueCapacity(500)
		executor.setThreadNamePrefix("microservices-")
		executor.initialize()
		return executor
	}
}
