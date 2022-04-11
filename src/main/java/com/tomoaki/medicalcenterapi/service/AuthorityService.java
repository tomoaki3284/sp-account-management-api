package com.tomoaki.medicalcenterapi.service;

import com.tomoaki.medicalcenterapi.model.entity.UserRolePair;
import com.tomoaki.medicalcenterapi.repository.AuthorityRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthorityService {
	
	private AuthorityRepositoryImpl authorityRepository;
	
	@Autowired
	public AuthorityService(
		AuthorityRepositoryImpl authorityRepository
	) {
		this.authorityRepository = authorityRepository;
	}
	
	public Mono<Boolean> roleExistsByAppCode(String app, String role) {
		return authorityRepository.roleExistsByAppCode(app, role);
	}
	
	public Mono<UserRolePair> saveRolesByUserID(Long uid, String role) {
		UserRolePair userRolePair = new UserRolePair(uid, role);
		return authorityRepository.save(userRolePair);
	}
}
