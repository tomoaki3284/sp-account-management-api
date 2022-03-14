package com.tomoaki.medicalcenterapi.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;

public interface UserDetailsService {
	Mono<UserDetails> loadUserByUsername(String username) throws UsernameNotFoundException;
}
