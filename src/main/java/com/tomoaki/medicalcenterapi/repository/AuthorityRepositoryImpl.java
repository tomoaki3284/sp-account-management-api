package com.tomoaki.medicalcenterapi.repository;

import com.tomoaki.medicalcenterapi.model.entity.UserRolePair;
import com.tomoaki.medicalcenterapi.model.yaml.RoleRegistry;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class AuthorityRepositoryImpl {
	
	private final Map<String, List<RoleRegistry>> roleRegistriesByApp;
	
	private AuthorityRepository authorityRepository;
	
	@Autowired
	public AuthorityRepositoryImpl(
		Map<String, List<RoleRegistry>> roleRegistriesByApp,
		AuthorityRepository authorityRepository
	) {
		this.roleRegistriesByApp = roleRegistriesByApp;
		this.authorityRepository = authorityRepository;
	}
	
	/**
	 * Return whether role exists in given app
	 *
	 * @param app
	 * @param role
	 * @return boolean
	 */
	private boolean roleExistsByAppCode(String app, String role) {
		List<RoleRegistry> roleRegistries = roleRegistriesByApp.get(app);
		
		if (roleRegistries == null || roleRegistries.size() == 0) {
			return false;
		}
		
		return roleRegistries
			.stream()
			.anyMatch(roleRegistry -> roleRegistry.getRole().equals(role));
	}
	
	private Mono<UserRolePair> findById(Long uid) {
		return authorityRepository.findById(uid);
	}
	
	private Mono<UserRolePair> save(Long uid, String role) {
		UserRolePair userRolePair = new UserRolePair(uid, role);
		return authorityRepository.save(userRolePair);
	}
}
