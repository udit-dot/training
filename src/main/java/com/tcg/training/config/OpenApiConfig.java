package com.tcg.training.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class OpenApiConfig {

	@Bean
	public OpenAPI productServiceOpenAPI() {
		Server devServer = new Server();
		devServer.setUrl("http://localhost:8083");
		devServer.setDescription("Development server");

		Info info = new Info().title("Product Management Microservice API").version("1.0")
				.description("This API exposes endpoints to manage products in the TCG system.");

		return new OpenAPI().info(info).servers(List.of(devServer));
	}
}