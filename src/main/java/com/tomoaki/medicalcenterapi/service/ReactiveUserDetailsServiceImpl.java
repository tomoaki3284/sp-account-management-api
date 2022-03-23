package com.tomoaki.medicalcenterapi.service;

import com.tomoaki.medicalcenterapi.exception.UserSaveException;
import com.tomoaki.medicalcenterapi.model.User;
import com.tomoaki.medicalcenterapi.model.UserDetailsImpl;
import com.tomoaki.medicalcenterapi.repository.UserRepository;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ReactiveUserDetailsServiceImpl implements ReactiveUserDetailsService {
	
	private UserRepository userRepository;
	
	@Autowired
	public ReactiveUserDetailsServiceImpl(
		UserRepository userRepository
	) {
		this.userRepository = userRepository;
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
}
