package com.tomoaki.medicalcenterapi.controller;

import com.tomoaki.medicalcenterapi.model.request.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@PostMapping("/signin")
	public Mono<ResponseEntity<?>> authenticateUser(
		@RequestBody LoginRequest loginRequest
	) {
		// todo
		return Mono.just(ResponseEntity.ok(null));
	}
	
	@PostMapping("/signup")
	public Mono<ResponseEntity<?>> registerUser(
		@RequestBody LoginRequest loginRequest
	) {
		// todo
		return Mono.just(ResponseEntity.ok(null));
	}
}
