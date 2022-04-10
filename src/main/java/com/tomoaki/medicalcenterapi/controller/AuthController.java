package com.tomoaki.medicalcenterapi.controller;

import com.tomoaki.medicalcenterapi.model.Response;
import com.tomoaki.medicalcenterapi.model.User;
import com.tomoaki.medicalcenterapi.security.JwtUtils;
import com.tomoaki.medicalcenterapi.service.ReactiveUserDetailsServiceImpl;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * Controller to authenticate and authorise user
 *
 * @author tmitsuhashi9621
 * @since 3/14/2022
 */
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	private ReactiveUserDetailsServiceImpl reactiveUserDetailsService;
	
	@PostMapping("/login")
	public Mono<ResponseEntity<?>> authenticateUser(
		@RequestBody Map<String,String> map
	) {
		// OPTION 1 to return username
		Mono<Authentication> authenticationMono = reactiveUserDetailsService.login(map.get("username"), map.get("password"));
		return authenticationMono
			.flatMap(auth -> Mono.just(
				ResponseEntity.ok()
					.header(HttpHeaders.AUTHORIZATION, (String) auth.getCredentials())
					.body(new Response("success"))
			));
	}
	
	@PostMapping(value = "/signup")
	public Mono<ResponseEntity<?>> registerUser(
		@RequestBody User user
	) {
		Mono<Integer> emailExist = reactiveUserDetailsService.existsByEmail(user.getEmail());
		Mono<Integer> usernameExist = reactiveUserDetailsService.existsByUsername(user.getUsername());
		
		// zip the result
		return Mono.zip(emailExist, usernameExist)
			.flatMap(result -> {
				// if email already exist
				if (result.getT1() == 1) {
					return Mono.just(
						ResponseEntity
							.status(HttpStatus.CONFLICT)
							.body(new Response("email exists"))
					);
				}
				// if username already exist
				else if (result.getT2() == 1) {
					return Mono.just(
						ResponseEntity
							.status(HttpStatus.CONFLICT)
							.body(new Response("username exists"))
					);
				}
				// if success
				else {
					return reactiveUserDetailsService
						.saveUser(user)
						.flatMap(success -> Mono.just(
							ResponseEntity
								.ok()
								.body(new Response("success"))
						));
				}
			});
	}
}
