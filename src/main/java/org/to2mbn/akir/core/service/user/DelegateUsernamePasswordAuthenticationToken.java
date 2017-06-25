package org.to2mbn.akir.core.service.user;

import java.util.Collection;
import java.util.function.Function;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class DelegateUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

	private static final long serialVersionUID = 1L;

	private Function<Object, Collection<GrantedAuthority>> authoritiesAccessor;

	public DelegateUsernamePasswordAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
		super(principal, credentials, authorities);
	}

	public DelegateUsernamePasswordAuthenticationToken(Object principal, Object credentials) {
		super(principal, credentials);
	}

	@Override
	public Collection<GrantedAuthority> getAuthorities() {
		return authoritiesAccessor.apply(getPrincipal());
	}

	public void setAuthoritiesAccessor(Function<Object, Collection<GrantedAuthority>> authoritiesAccessor) {
		this.authoritiesAccessor = authoritiesAccessor;
	}

}
