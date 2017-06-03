package org.to2mbn.akir.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.to2mbn.akir.core.model.User;
import org.to2mbn.akir.core.repository.UserRepository;

@Component
public class UserService implements UserDetailsService {

	public static User getUserFromAuthentication(Authentication auth) {
		if (auth instanceof UsernamePasswordAuthenticationToken) {
			Object principal = auth.getPrincipal();
			if (principal instanceof AkirUserDetails) {
				return ((AkirUserDetails) principal).getUserModel();
			}
		}
		throw new AuthenticationServiceException("Invalid authentication");
	}

	@Autowired
	private UserRepository repository;

	@Autowired
	private AuthenticationManager authProvider;

	@Override
	public AkirUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return repository.findById(email)
				.map(AkirUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException(email));
	}

	public User login(String email, String password) throws AuthenticationException {
		return getUserFromAuthentication(authProvider.authenticate(new UsernamePasswordAuthenticationToken(email, password)));
	}

}
