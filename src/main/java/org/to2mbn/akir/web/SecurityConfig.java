package org.to2mbn.akir.web;

import static org.to2mbn.akir.web.util.WebUtils.isAjax;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.to2mbn.akir.core.model.User;
import org.to2mbn.akir.core.service.user.UserService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationEntryPoint authEntry;

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

				// redirect to '/email_verify' is email is not verified
				.accessDeniedHandler((request, response, ex) -> {
					if (!isAjax(request)) {
						Optional<User> user = UserService.getCurrentUser();
						if (user.isPresent() && !user.get().isEmailVerified()) {
							response.sendRedirect("/email_verify");
							return;
						}
					}
					response.sendError(HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
				})
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
