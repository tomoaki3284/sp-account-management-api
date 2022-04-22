package com.tomoaki.medicalcenterapi.controller;

import com.tomoaki.medicalcenterapi.model.Response;
import com.tomoaki.medicalcenterapi.model.entity.User;
import com.tomoaki.medicalcenterapi.security.JwtUtils;
import com.tomoaki.medicalcenterapi.service.AuthorityService;
import com.tomoaki.medicalcenterapi.service.ReactiveUserDetailsServiceImpl;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
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
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	private JwtUtils jwtUtils;
	
	private ReactiveUserDetailsServiceImpl reactiveUserDetailsService;
	
	private AuthorityService authorityService;
	
	@Autowired
	public AuthController(
		JwtUtils jwtUtils,
		ReactiveUserDetailsServiceImpl reactiveUserDetailsService,
		AuthorityService authorityService
	) {
		this.jwtUtils = jwtUtils;
		this.reactiveUserDetailsService = reactiveUserDetailsService;
		this.authorityService = authorityService;
	}
	
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
	
	@PostMapping(path = "/signup/{appCode}")
	public Mono<ResponseEntity<?>> registerUser(
		@PathVariable String appCode,
		@RequestBody Map<String,Object> requestBody
	) {
		String email = (String) requestBody.get("email");
		String username = (String) requestBody.get("username");
		String password = (String) requestBody.get("password");
		String role = (String) requestBody.get("role");
		
		Mono<Integer> emailExist = reactiveUserDetailsService.existsByEmail(email);
		Mono<Integer> usernameExist = reactiveUserDetailsService.existsByUsername(username);
		
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
					return authorityService
						// check if role exists
						.roleExistsByAppCode(appCode, role)
						.filter(Boolean::booleanValue)
						// at this point role exists, so save user
						.flatMap(success -> {
							User user = new User(username, email, password);
							return reactiveUserDetailsService.saveUser(user);
						})
						// when user couldn't be save, log the err and output Mono.empty()
						.doOnError(err -> logger.error("couldn't save user {err}", err))
						.onErrorResume(err -> Mono.empty())
						// Here, user is successfully saved, so save the role
						.flatMap(user -> authorityService.saveRolesByUserID(user.getId(), role))
						// when userId and role couldn't be save, log the err and output Mono.empty()
						.doOnError(err -> logger.error("couldn't save useId and role {err}", err))
						.onErrorResume(err -> Mono.empty())
						// Here, role is saved, so response with 200 - ok
						.flatMap(res -> createResponseEntity("success", HttpStatus.OK))
						// otherwise, return with proper status code
						.switchIfEmpty(Mono.defer(() -> createResponseEntity("server save failure, check if role exists", HttpStatus.INSUFFICIENT_STORAGE)));
				}
			});
	}
	
	private <T> Mono<ResponseEntity<T>> createResponseEntity(T body, HttpStatus statusCode) {
		return Mono.just(new ResponseEntity<T>(body, statusCode));
	}
}
