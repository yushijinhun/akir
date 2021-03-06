package org.to2mbn.akir.web;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static org.to2mbn.akir.web.util.WebUtils.ERROR_ATTRIBUTE;
import static org.to2mbn.akir.web.util.WebUtils.isAjax;
import static org.to2mbn.akir.web.util.exception.ErrorMessage.E_ACCESS_DENIED;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.to2mbn.akir.core.model.User;
import org.to2mbn.akir.core.service.AkirConfiguration;
import org.to2mbn.akir.core.service.user.AkirAuthenticationProvider;
import org.to2mbn.akir.core.service.user.UserService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityConfig.class);

	@Autowired
	private AkirAuthenticationProvider authenticationProvider;

	@Autowired
	private AuthenticationEntryPoint authEntry;

	@Autowired
	private AkirConfiguration config;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		if (config.getUrl().startsWith("https://")) {
			LOGGER.info("Enabled force https");
			http.requiresChannel().anyRequest().requiresSecure();
		}
		http
				// cache control
				.headers()
				.cacheControl().disable()
				.and()

				// access control
				.authorizeRequests()
				.antMatchers(

						// == static resources -> permit all
						"/css/**", "/images/**", "/js/**", "/fonts/**", "/favicon.ico",

						// == login/register/email_verify -> permit all
						"/register/**", "/login", "/email_verify/do_verify",

						// == error page -> permit all
						"/error",

						"/yggdrasil/**"

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

				// redirect to '/email_verify' is email is not verified
				.accessDeniedHandler((request, response, ex) -> {
					if (!isAjax(request)) {
						Optional<User> user = UserService.getCurrentUser();
						if (user.isPresent() && !user.get().isEmailVerified()) {
							response.sendRedirect("/email_verify");
							return;
						}
					}
					request.setAttribute(ERROR_ATTRIBUTE, ex);
					response.sendError(SC_FORBIDDEN, E_ACCESS_DENIED);
				})
				.and()

				// logout
				.logout()
				.logoutUrl("/logout")
				.logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler(HttpStatus.NO_CONTENT))
				.and();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(authenticationProvider);
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
