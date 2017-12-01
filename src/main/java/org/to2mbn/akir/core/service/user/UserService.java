package org.to2mbn.akir.core.service.user;

import static java.util.Objects.requireNonNull;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.to2mbn.akir.core.model.User;
import org.to2mbn.akir.core.repository.UserRepository;

@Component
public class UserService {

	public static final String REGEX_EMAIL =
			"(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
	public static final String REGEX_NAME = "[a-zA-Z0-9]([-_ ]?[a-zA-Z0-9]+)*";
	public static final int MAX_LENGTH_EMAIL = 254;
	public static final int MAX_LENGTH_NAME = 254;
	public static final int MIN_LENGTH_PASSWORD = 6;
	public static final int MAX_LENGTH_PASSWORD = 256;

	private static final Pattern PATTERN_EMAIL = Pattern.compile(REGEX_EMAIL);
	private static final Pattern PATTERN_NAME = Pattern.compile(REGEX_NAME);

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	public static Optional<User> getUserFromAuthentication(Authentication auth) {
		if (auth instanceof UsernamePasswordAuthenticationToken) {
			Object principal = auth.getPrincipal();
			if (principal instanceof AkirUserDetails) {
				return Optional.of(((AkirUserDetails) principal).getUserModel());
			}
		}
		return Optional.empty();
	}

	public static Optional<User> getCurrentUser() {
		return getCurrentAuthentication().flatMap(UserService::getUserFromAuthentication);
	}

	public static Optional<Authentication> getCurrentAuthentication() {
		return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication());
	}

	public static boolean hasAuthority(String authority) {
		requireNonNull(authority);
		return getCurrentAuthentication()
				.map(auth -> auth.getAuthorities().stream()
						.anyMatch(p -> authority.equals(p.getAuthority())))
				.orElse(false);
	}

	@Autowired
	private UserRepository repository;

	@Autowired
	private AuthenticationManager authProvider;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	public User login(String email, String password) throws AuthenticationException, InvalidCredentialsException {
		return getUserFromAuthentication(authenticate(email, password))
				.orElseThrow(() -> new AuthenticationServiceException("Invalid authentication"));
	}

	public Authentication authenticate(Authentication authentication) throws AuthenticationException, InvalidCredentialsException {
		try {
			return authProvider.authenticate(authentication);
		} catch (BadCredentialsException | UsernameNotFoundException e) {
			throw new InvalidCredentialsException(e);
		}
	}

	public Authentication authenticate(String email, String password) throws AuthenticationException, InvalidCredentialsException {
		return authenticate(new UsernamePasswordAuthenticationToken(
				repository.findByEmail(email.toLowerCase()).orElseThrow(InvalidCredentialsException::new).getId().toString(),
				password));
	}

	public User register(String email, String name, String password) throws UserConflictException {
		// check email
		if (email.length() > MAX_LENGTH_EMAIL)
			throw new IllegalArgumentException("Email is too long");
		if (!PATTERN_EMAIL.matcher(email).matches())
			throw new IllegalArgumentException("Invalid email");

		// check name
		if (name.length() > MAX_LENGTH_NAME)
			throw new IllegalArgumentException("Name is too long");
		if (!PATTERN_NAME.matcher(name).matches())
			throw new IllegalArgumentException("Invalid name");

		// check password
		if (password.length() < MIN_LENGTH_PASSWORD)
			throw new IllegalArgumentException("Password is too short");
		if (password.length() > MAX_LENGTH_PASSWORD)
			throw new IllegalArgumentException("Password is too long");

		// we only use lower-case email&name
		email = email.toLowerCase();
		name = name.toLowerCase();

		if (repository.existsByEmail(email))
			throw new UserConflictException("Email is already in use");
		if (repository.existsByName(name))
			throw new UserConflictException("Name is already in use");

		User user = new User();
		user.setId(UUID.randomUUID());
		user.setEmail(email);
		user.setName(name);
		user.setPasswordHash(passwordEncoder.encode(password));
		user.setEmailVerified(false);
		user.setAdmin(false);
		user.setRegisterTime(System.currentTimeMillis());
		user = repository.save(user);

		LOGGER.info("User {} registered with email {}", user.getId(), user.getEmail());

		eventPublisher.publishEvent(new UserRegistrationEvent(this, user.getId()));

		// re-find the user object as the user object might have been changed
		return repository.findById(user.getId()).get();
	}

	public User userOf(String name) throws UserNotFoundException {
		return repository.findByName(name).orElseThrow(() -> new UserNotFoundException(name));
	}

	@EventListener
	public void onLoginSuccess(AuthenticationSuccessEvent event) {
		LOGGER.info("User {} login successfully",
				event.getAuthentication().getPrincipal());
	}

	@EventListener
	public void onLogin(AbstractAuthenticationFailureEvent event) {
		LOGGER.info("User {} login failure: {}",
				event.getAuthentication().getPrincipal(),
				String.valueOf(event.getException()));
	}

}
