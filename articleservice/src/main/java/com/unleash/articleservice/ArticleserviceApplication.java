package com.unleash.articleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableFeignClients
@EnableTransactionManagement
public class ArticleserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArticleserviceApplication.class, args);
	}

}
