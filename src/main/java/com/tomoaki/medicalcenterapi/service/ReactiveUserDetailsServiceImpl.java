package com.tomoaki.medicalcenterapi.service;

import com.tomoaki.medicalcenterapi.exception.UserSaveException;
import com.tomoaki.medicalcenterapi.filter.JwtAuthenticationToken;
import com.tomoaki.medicalcenterapi.model.UserDetailsImpl;
import com.tomoaki.medicalcenterapi.model.entity.User;
import com.tomoaki.medicalcenterapi.repository.UserRepository;
import com.tomoaki.medicalcenterapi.security.JwtUtils;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {
	
	private static final Logger logger = LoggerFactory.getLogger(ReactiveUserDetailsServiceImpl.class);
	
	private UserRepository userRepository;
	private JwtUtils jwtUtils;
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	public ReactiveUserDetailsServiceImpl(
		UserRepository userRepository,
		PasswordEncoder passwordEncoder,
		JwtUtils jwtUtils
	) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtils = jwtUtils;
	}
	
	@Override
	public Mono<UserDetails> findByUsername(String username) {
		return userRepository
			.findByUsername(username)
			.switchIfEmpty(Mono.error(new UsernameNotFoundException("username not found: username=" + username)))
			.map(UserDetailsImpl::build);
	}
	
	public Mono<Boolean> saveUser(User user) {
		LocalDate date = LocalDate.now();
		user.setDate(date);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository
			.save(user)
			.switchIfEmpty(Mono.defer(() -> Mono.error(new UserSaveException("user couldn't be save"))))
			.flatMap(u -> Mono.just(true));
	}
	
	public Mono<Integer> existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}
	
	public Mono<Integer> existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}
	
	public Mono<Authentication> login(String username, String password) {
		return userRepository
			.findByUsername(username)
			.switchIfEmpty(Mono.defer(() -> {
				logger.info("username cannot be found");
				return Mono.error(new UsernameNotFoundException("username: " + username));
			}))
			.filter(user -> passwordEncoder.matches(password, user.getPassword()))
			.switchIfEmpty(Mono.defer(() -> {
				logger.info("password don't match");
				return Mono.error(new BadCredentialsException("invalid login credentials"));
			}))
			.map(user -> {
				logger.info("user found and valid login credentials");
				UserDetailsImpl u = UserDetailsImpl.build(user);
				return new JwtAuthenticationToken(jwtUtils.generateJwtToken(u));
			});
	}
}
