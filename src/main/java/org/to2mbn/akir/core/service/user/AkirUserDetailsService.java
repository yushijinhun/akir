package org.to2mbn.akir.core.service.user;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.to2mbn.akir.core.repository.UserRepository;

@Component
public class AkirUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Override
	public AkirUserDetails loadUserByUsername(String userIdStr) throws UsernameNotFoundException {
		UUID id;
		try {
			id = UUID.fromString(userIdStr);
		} catch (IllegalArgumentException e) {
			throw new UsernameNotFoundException(userIdStr);
		}

		if (!repository.existsById(id))
			throw new UsernameNotFoundException(userIdStr);

		return new AkirUserDetails(repository::findById, id);
	}
}
