package org.to2mbn.akir.core.service;

import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.to2mbn.akir.core.model.User;
import org.to2mbn.akir.core.repository.UserRepository;

@Component
public class UserService implements UserDetailsService {

	private static final Pattern PATTERN_EMAIL = Pattern.compile("(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
	private static final Pattern PATTERN_NAME = Pattern.compile("[a-zA-Z]([-_]?[a-zA-Z0-9]+)*");

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

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public AkirUserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return repository.findById(email)
				.map(AkirUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException(email));
	}

	public User login(String email, String password) throws AuthenticationException {
		return getUserFromAuthentication(authProvider.authenticate(new UsernamePasswordAuthenticationToken(email, password)));
	}

	public User register(String email, String name, String password) {
		if (name.length() > 32)
			throw new IllegalArgumentException("Name is too long");
		if (password.length() < 6)
			throw new IllegalArgumentException("Password is too short");
		if (!PATTERN_EMAIL.matcher(email).matches())
			throw new IllegalArgumentException("Invalid email");
		if (!PATTERN_NAME.matcher(name).matches())
			throw new IllegalArgumentException("Invalid name");
		email = email.toLowerCase();
		name = name.toLowerCase();
		if (repository.existsById(email))
			throw new IllegalArgumentException("Email is already in use");
		if (repository.existsByName(name))
			throw new IllegalArgumentException("Name is already in use");

		User user = new User();
		user.setEmail(email);
		user.setName(name);
		user.setPasswordHash(passwordEncoder.encode(password));
		return repository.save(user);
	}

}
