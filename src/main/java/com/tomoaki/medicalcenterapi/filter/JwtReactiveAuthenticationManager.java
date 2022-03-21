package com.tomoaki.medicalcenterapi.filter;

import com.tomoaki.medicalcenterapi.security.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@Qualifier("jwt")
public class JwtReactiveAuthenticationManager implements ReactiveAuthenticationManager {
	
	private final JwtUtils jwtUtil;
	private final ReactiveUserDetailsService userDetailsService;
	
	@Autowired
	public JwtReactiveAuthenticationManager(JwtUtils jwtUtil, ReactiveUserDetailsService userDetailsService) {
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	public Mono<Authentication> authenticate(Authentication authentication) {
		String token = (String) authentication.getCredentials();
		
		String username;
		try {
			if(!this.jwtUtil.validateJwtToken(token)) throw new Exception();
			username = this.jwtUtil.getUserNameFromJwtToken(token);
		} catch (Exception e) {
			return Mono.error(new BadCredentialsException("invalid credentials"));
		}
		
		// look-up by username, if found, return authenticated JwtAuthToken
		// if not found, return exception
		return this.userDetailsService.findByUsername(username)
			.switchIfEmpty(Mono.error(new BadCredentialsException("invalid credentials")))
			.map(userDetails -> new JwtAuthenticationToken(userDetails, token, userDetails.getAuthorities()));
	}
}
