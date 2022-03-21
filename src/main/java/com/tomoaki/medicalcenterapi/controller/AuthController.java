package com.tomoaki.medicalcenterapi.controller;

import com.tomoaki.medicalcenterapi.repository.UserRepository;
import com.tomoaki.medicalcenterapi.security.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
	private UserRepository userRepository;
	
	@Autowired
	private JwtUtils jwtUtils;
	
	@Autowired
	public AuthController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@PostMapping("/login")
	public Mono<ResponseEntity<?>> authenticateUser(
		Authentication authentication
	) {
		// OPTION 1 to return username
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return Mono.just(
			ResponseEntity.ok()
				.header(HttpHeaders.AUTHORIZATION, jwtUtils.generateJwtToken(authentication))
				.body("Hi " + userDetails.getPassword() + ", you are just logged in")
		);
	}
	
	@PostMapping("/signup")
	public Mono<ResponseEntity<?>> registerUser(
		@RequestBody String loginRequest
	) {
		// todo
		return Mono.just(ResponseEntity.ok(loginRequest));
	}
}
