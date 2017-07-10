package org.to2mbn.akir.core.service;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class ServiceConfig {

	@Bean
	public Executor emailExecutor() {
		return Executors.newFixedThreadPool(2);
	}

}
