package com.tomoaki.medicalcenterapi.service;

import com.tomoaki.medicalcenterapi.model.User;
import com.tomoaki.medicalcenterapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import reactor.core.publisher.Mono;

public class UserDetailsServiceImpl implements UserDetailsService {
	
	private UserRepository userRepository;
	
	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}
	
	@Override
	public Mono<UserDetails> loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository
			.findByUsername(username)
			.map(res -> {
				if (res.isPresent()) {
					User user = res.get();
					return UserDetailsImpl.build(user);
				}
				
				throw new UsernameNotFoundException("Username not found username=" + username);
			});
	}
}
