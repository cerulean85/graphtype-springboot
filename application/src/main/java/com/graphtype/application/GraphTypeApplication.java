package com.graphtype.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class GraphTypeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GraphTypeApplication.class, args);
	}

}
