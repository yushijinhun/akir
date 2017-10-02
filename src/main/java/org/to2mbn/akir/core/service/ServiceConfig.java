package org.to2mbn.akir.core.service;

import static java.util.Collections.singletonList;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.to2mbn.akir.core.service.user.AkirAuthenticationProvider;

@Configuration
@EnableAsync
public class ServiceConfig {

	@Autowired
	private AkirAuthenticationProvider authenticationProvider;

	@Bean
	public Executor emailExecutor() {
		return Executors.newFixedThreadPool(2);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		return new ProviderManager(singletonList(authenticationProvider));
	}

}
