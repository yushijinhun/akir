package org.to2mbn.akir.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.to2mbn.akir.web.util.exception.AccessDeniedController;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationEntryPoint authEntry;

	@Autowired
	private AccessDeniedController accessDeniedController;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				// cache control
				.headers()
				.cacheControl().disable()
				.and()

				// access control
				.authorizeRequests()
				.antMatchers(

						// == static resources -> permit all
						"/css/**", "/images/**", "/js/**", "/favicon.ico",

						// == login/register/email_verify -> permit all
						"/register/**", "/login", "/email_verify/do_verify"

				).permitAll()
				.antMatchers(

						// == (re)send_verify_email -> permit authenticated users
						"/email_verify/**"

				).authenticated()

				// == other urls -> permit verified users
				.anyRequest().hasAuthority("ROLE_VERIFIED")
				.and()

				// login
				.exceptionHandling()
				.authenticationEntryPoint(authEntry)
				.accessDeniedHandler(accessDeniedController)
				.and()

				// logout
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessUrl("/login")
				.and();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
