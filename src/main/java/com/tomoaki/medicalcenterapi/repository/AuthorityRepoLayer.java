package com.tomoaki.medicalcenterapi.repository;

import com.tomoaki.medicalcenterapi.model.entity.UserRolePair;
import com.tomoaki.medicalcenterapi.model.yaml.RoleRegistry;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthorityRepoLayer {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthorityRepoLayer.class);
	
	private final Map<String, List<RoleRegistry>> roleRegistriesByApp;
	
	private AuthorityRepository authorityRepository;
	
	@Autowired
	public AuthorityRepoLayer(
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
	public Mono<Boolean> roleExistsByAppCode(String app, String role) {
		List<RoleRegistry> roleRegistries = roleRegistriesByApp.get(app);
		
		if (roleRegistries == null || roleRegistries.size() == 0) {
			return Mono.just(false);
		}
		
		boolean res = roleRegistries
			.stream()
			.anyMatch(roleRegistry -> roleRegistry.getRole().equals(role));
		
		return Mono.just(res);
	}
	
	/**
	 * Returns roleRegistry that is in the roleNames
	 *
	 * @param appCode
	 * @param roleNames
	 * @return
	 */
	public Mono<List<RoleRegistry>> getRoleRegistriesByAppCode(String appCode, List<String> roleNames) {
		return Mono.just(
			roleRegistriesByApp
				.get(appCode)
				.stream()
				.filter(roleRegistry -> roleNames.contains(roleRegistry.getRole()))
				.collect(Collectors.toList())
		);
	}
	
	public Mono<UserRolePair> findById(Long uid) {
		return authorityRepository.findById(uid);
	}
	
	public Mono<UserRolePair> save(UserRolePair userRolePair) {
		logger.debug("userRolePair save {}", userRolePair);
		return authorityRepository.save(userRolePair);
	}
}
