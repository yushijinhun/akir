package org.to2mbn.akir.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.to2mbn.akir.web.login.ReturnableLoginUrlAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// access control
				.authorizeRequests()
				.antMatchers(

						"/css/**", "/img/**", "/js/**", "/favicon.ico",
						"/register", "/login"

				).permitAll()
				.anyRequest().authenticated()

				.and()

				// login
				.exceptionHandling()
				.authenticationEntryPoint(
						new ReturnableLoginUrlAuthenticationEntryPoint("/login"))

		;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
