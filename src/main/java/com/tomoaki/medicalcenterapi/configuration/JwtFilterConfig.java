package com.tomoaki.medicalcenterapi.configuration;

import com.tomoaki.medicalcenterapi.filter.JwtReactiveAuthenticationManager;
import com.tomoaki.medicalcenterapi.security.JwtAuthenticationConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.WebFilterChainServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import reactor.core.publisher.Mono;

/**
 * Necessary setup/configuration for jwt filter.
 *
 * @author tmitsuhashi9621
 * @since 3/22/2022
 */
@Configuration
public class JwtFilterConfig {
	
	private static final String[] AUTH_REQUIRED_PATTERNS = {"/resource/**"};
	
	private ReactiveUserDetailsService userDetailsService;
	private JwtReactiveAuthenticationManager jwtReactiveAuthenticationManager;
	private JwtAuthenticationConverter jwtAuthenticationConverter;
	
	@Autowired
	public JwtFilterConfig(ReactiveUserDetailsService userDetailsService,
		@Qualifier("jwt") JwtReactiveAuthenticationManager jwtReactiveAuthenticationManager,
		JwtAuthenticationConverter jwtAuthenticationConverter) {
		this.userDetailsService = userDetailsService;
		this.jwtReactiveAuthenticationManager = jwtReactiveAuthenticationManager;
		this.jwtAuthenticationConverter = jwtAuthenticationConverter;
	}
	
	@Bean(name="jwtWebFilter")
	@Primary
	AuthenticationWebFilter jwtAuthenticationWebFilter(){
		// create a filter from custom jwt auth manager
		AuthenticationWebFilter filter = new AuthenticationWebFilter(this.jwtReactiveAuthenticationManager);
		
		// need to have a request converter before the request reaches to filter, so setting it here
		filter.setServerAuthenticationConverter(this.jwtAuthenticationConverter);
		
		filter.setAuthenticationSuccessHandler(new WebFilterChainServerAuthenticationSuccessHandler());
		filter.setAuthenticationFailureHandler((exchange, exception) ->
			Mono.error(new BadCredentialsException("Wrong authentication token")));
		
		// set the path that requires this auth filter
		filter.setRequiresAuthenticationMatcher(ServerWebExchangeMatchers.pathMatchers("/resource/**"));
		
		return filter;
	}
}
