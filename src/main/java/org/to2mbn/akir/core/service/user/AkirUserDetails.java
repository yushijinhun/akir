package org.to2mbn.akir.core.service.user;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.to2mbn.akir.core.model.User;

public class AkirUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Function<String, Optional<User>> userAccessor;
	private String userId;

	private static final Set<GrantedAuthority> AUTHORITIES_UNVERIFIED = Collections.emptySet();
	private static final Set<GrantedAuthority> AUTHORITIES_VERIFIED = Collections.singleton(new SimpleGrantedAuthority("ROLE_VERIFIED"));

	public AkirUserDetails(Function<String, Optional<User>> userAccessor, String userId) {
		this.userAccessor = userAccessor;
		this.userId = userId;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getUserModel().isEmailVerified() ? AUTHORITIES_VERIFIED : AUTHORITIES_UNVERIFIED;
	}

	@Override
	public String getPassword() {
		return getUserModel().getPasswordHash();
	}

	@Override
	public String getUsername() {
		return userId;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public User getUserModel() {
		return userAccessor.apply(userId).orElseThrow(() -> new UsernameNotFoundException(userId));
	}

	@Override
	public String toString() {
		return getUserModel().getEmail();
	}

}
