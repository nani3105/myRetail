package com.kurra.myRetail

import com.kurra.myRetail.exception.RestTemplateResponseErrorHandler
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.web.client.RestTemplate

@SpringBootApplication
class MyRetailApplication {

	static void main(String[] args) {
		SpringApplication.run MyRetailApplication, args
	}

	@Bean
	RestTemplate restTemplate(RestTemplateBuilder builder, RestTemplateResponseErrorHandler errorHandler) {
		return builder.errorHandler(errorHandler).build()
	}
}
