package com.tomoaki.medicalcenterapi.service;

import com.tomoaki.medicalcenterapi.model.entity.UserRolePair;
import com.tomoaki.medicalcenterapi.repository.AuthorityRepoLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthorityService {
	
	private AuthorityRepoLayer authorityRepoLayer;
	
	@Autowired
	public AuthorityService(
		AuthorityRepoLayer authorityRepoLayer
	) {
		this.authorityRepoLayer = authorityRepoLayer;
	}
	
	public Mono<Boolean> roleExistsByAppCode(String app, String role) {
		return authorityRepoLayer.roleExistsByAppCode(app, role);
	}
	
	public Mono<UserRolePair> saveRolesByUserID(Long uid, String role) {
		UserRolePair userRolePair = new UserRolePair(uid, role);
		return authorityRepoLayer.save(userRolePair);
	}
}
