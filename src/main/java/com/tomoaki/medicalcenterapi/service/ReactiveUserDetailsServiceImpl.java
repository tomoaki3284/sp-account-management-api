package com.tomoaki.medicalcenterapi.service;

import com.tomoaki.medicalcenterapi.model.UserDetailsImpl;
import com.tomoaki.medicalcenterapi.repository.UserRepository;
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
}
