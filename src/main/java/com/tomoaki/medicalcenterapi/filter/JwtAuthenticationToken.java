package com.tomoaki.medicalcenterapi.filter;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
	private String token;
	private UserDetails userDetails;
	
	public JwtAuthenticationToken(String token) {
		super(AuthorityUtils.NO_AUTHORITIES);
		this.token = token;
	}
	
	public JwtAuthenticationToken(UserDetails userDetails,
		String token,
		Collection<? extends GrantedAuthority> authorities) {
		super(authorities);
		this.token = token;
		this.userDetails = userDetails;
		this.setAuthenticated(true);
	}
	
	@Override
	public Object getCredentials() {
		return this.token;
	}
	
	@Override
	public Object getPrincipal() {
		return this.userDetails;
	}
}
