package org.to2mbn.akir.core.service.user;

import java.util.Collection;
import java.util.Collections;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AkirAuthenticationProvider extends DaoAuthenticationProvider {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
		DelegateUsernamePasswordAuthenticationToken result = new DelegateUsernamePasswordAuthenticationToken(principal, authentication.getCredentials(), null);
		result.setDetails(authentication.getDetails());
		result.setAuthoritiesAccessor(this::getAuthorities);
		return result;
	}

	private Collection<GrantedAuthority> getAuthorities(Object principal) {
		return Collections.unmodifiableCollection(((UserDetails) principal).getAuthorities());
	}

	@PostConstruct
	private void setup() {
		setUserDetailsService(userDetailsService);
		setPasswordEncoder(passwordEncoder);
	}

}
