package com.tomoaki.medicalcenterapi.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.HttpStatusServerEntryPoint;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import reactor.core.publisher.Mono;

/**
 * Security Configuration for WebFlux.
 *
 * @author tmitsuhashi9621
 * @since 3/22/2022
 */
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebFluxSecurityConfiguration {
	
	@Autowired
	private AuthenticationWebFilter jwtAuthenticationWebFilter;
	
	/**
	 * Set up a filter before the request reaches to the controller.
	 *
	 * @param http
	 * @return SecurityWebFilterChain
	 */
	@Bean
	public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) {
		String[] patterns = new String[]{"/api/v1/auth/**"};
		
		/*
			For auth routes, client can access it without jwt. so permit all for patterns routes
			Anything else, client need jwt. so authentication is required
		 */
		http
			.csrf().disable()
			
			// handle exception
			.exceptionHandling()
			.authenticationEntryPoint(new HttpStatusServerEntryPoint(HttpStatus.FORBIDDEN))
			.accessDeniedHandler((exchange, exception) -> Mono.error(exception))
			
			// no session, make it stateless
			.and()
			.securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
			
			// disable spring security default login form
			// because we have our simple custom logic in service
			.formLogin()
			.disable()
			
			// allow patterns path access without authentication,
			// and anything else needs authentication
			.authorizeExchange()
			.pathMatchers("/auth/**").permitAll()
			.pathMatchers("/resource/**").authenticated()
			
			// add jwt auth filter at Authentication phase
			.and()
			.addFilterAt(jwtAuthenticationWebFilter, SecurityWebFiltersOrder.AUTHENTICATION)
			.httpBasic();
		
		return http.build();
	}
}
