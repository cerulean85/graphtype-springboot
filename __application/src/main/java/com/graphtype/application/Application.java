package com.graphtype.application;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)

public class Application {

	public static void main(String[] args) {
//		SpringApplication.run(Application.class, args);
		SpringApplication app = new SpringApplication(Application.class);
		app.setWebApplicationType(WebApplicationType.SERVLET);
		app.run(args);
	}

}
