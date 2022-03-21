package com.tomoaki.medicalcenterapi.security;

import java.util.Base64;
import java.util.function.Function;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public class ServerHttpBasicAuthenticationConverter implements Function<ServerWebExchange, Mono<Authentication>> {
	
	public static final String BASIC = "Basic ";
	
	@Override
	public Mono<Authentication> apply(ServerWebExchange serverWebExchange) {
		ServerHttpRequest request = serverWebExchange.getRequest();
		String authorization = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
		if (!StringUtils.startsWithIgnoreCase(authorization, BASIC)) {
			return Mono.empty();
		}
		
		String credentials = (authorization.length() <= BASIC.length()) ? ""
			: authorization.substring(BASIC.length());
		String decodedCredentials = new String(base64Decode(credentials));
		String[] parts = decodedCredentials.split(":", 2);
		if (parts.length != 2) {
			return Mono.empty();
		}
		String username = parts[0];
		String token = parts[1];
		return Mono.just(new UsernamePasswordAuthenticationToken(username, token));
	}
	
	private byte[] base64Decode(String value) {
		try {
			return Base64.getDecoder().decode(value);
		}
		catch (Exception ex) {
			return new byte[0];
		}
	}
}
