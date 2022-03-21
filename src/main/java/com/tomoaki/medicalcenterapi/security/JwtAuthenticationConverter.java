package com.tomoaki.medicalcenterapi.security;

import com.tomoaki.medicalcenterapi.filter.JwtAuthenticationToken;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationConverter implements ServerAuthenticationConverter {
	
	public static final String AUTH_HEADER_PREFIX = "Bearer ";
	
	@Override
	public Mono<Authentication> convert(ServerWebExchange exchange) {
		String authorization = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		
		assert authorization != null;
		if (!authorization.startsWith(AUTH_HEADER_PREFIX)) {
			return Mono.empty();
		}
		
		return Mono.just(new JwtAuthenticationToken(authorization.substring(AUTH_HEADER_PREFIX.length())));
	}
}
