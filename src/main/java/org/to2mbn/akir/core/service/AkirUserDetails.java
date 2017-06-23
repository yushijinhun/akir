package org.to2mbn.akir.core.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.to2mbn.akir.core.model.User;

public class AkirUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private User userModel;

	private static final Set<GrantedAuthority> AUTHORITIES_UNVERIFIED = Collections.emptySet();
	private static final Set<GrantedAuthority> AUTHORITIES_VERIFIED = Collections.singleton(new SimpleGrantedAuthority("ROLE_VERIFIED"));

	public AkirUserDetails(User userModel) {
		this.userModel = Objects.requireNonNull(userModel);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return userModel.isEmailVerified() ? AUTHORITIES_VERIFIED : AUTHORITIES_UNVERIFIED;
	}

	@Override
	public String getPassword() {
		return userModel.getPasswordHash();
	}

	@Override
	public String getUsername() {
		return userModel.getEmail();
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
		return userModel;
	}

}
