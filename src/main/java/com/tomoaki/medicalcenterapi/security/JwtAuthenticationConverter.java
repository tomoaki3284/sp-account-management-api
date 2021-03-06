package com.tomoaki.medicalcenterapi.security;

import com.tomoaki.medicalcenterapi.filter.JwtAuthenticationToken;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * Converter for jwt auth filtering
 *
 * @author tmitsuhashi9621
 * @since 3/22/2022
 */
@Component
public class JwtAuthenticationConverter implements ServerAuthenticationConverter {
	
	public static final String AUTH_HEADER_PREFIX = "Bearer ";
	
	/**
	 * Extract the AUTHORIZATION header value  in the request, and convert it and return the
	 * Mono of jwt auth token
	 *
	 * @param exchange
	 * @return
	 */
	@Override
	public Mono<Authentication> convert(ServerWebExchange exchange) {
		String authorization = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		
		assert authorization != null;
		// todo: this generate NPE when no "bearer " token is attached to the header
		if (!authorization.startsWith(AUTH_HEADER_PREFIX)) {
			return Mono.empty();
		}
		
		return Mono.just(new JwtAuthenticationToken(authorization.substring(AUTH_HEADER_PREFIX.length())));
	}
}
