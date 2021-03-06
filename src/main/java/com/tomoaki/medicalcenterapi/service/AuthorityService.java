package com.tomoaki.medicalcenterapi.service;

import com.tomoaki.medicalcenterapi.controller.GeneralController;
import com.tomoaki.medicalcenterapi.model.entity.UserRolePair;
import com.tomoaki.medicalcenterapi.model.yaml.RoleRegistry;
import com.tomoaki.medicalcenterapi.repository.AuthorityRepoLayer;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthorityService {
	
	private static final Logger logger = LoggerFactory.getLogger(GeneralController.class);
	
	private AuthorityRepoLayer authorityRepoLayer;
	
	@Autowired
	public AuthorityService(
		AuthorityRepoLayer authorityRepoLayer
	) {
		this.authorityRepoLayer = authorityRepoLayer;
	}
	
	public Mono<List<RoleRegistry>> getRoleRegistriesById(Long userId, String appCode) {
		return authorityRepoLayer
			.getRolesById(userId)
			.flatMap(roles -> authorityRepoLayer
				.getRoleRegistriesByAppCode(appCode, roles)
			)
			.doOnNext(roleRegistries -> logger.info("roleRegistries: {}", Arrays.toString(roleRegistries.toArray())));
	}
	
	public Mono<Boolean> roleExistsByAppCode(String app, String role) {
		return authorityRepoLayer.roleExistsByAppCode(app, role);
	}
	
	public Mono<UserRolePair> saveRolesByUserID(Long uid, String role) {
		UserRolePair userRolePair = new UserRolePair(uid, role);
		return authorityRepoLayer.save(userRolePair);
	}
	
	public Mono<Boolean> queryNotAccessForbiddenTables(
		String queryCommand,
		List<String> accessTables,
		Map<String, List<String>> accessFieldsByTables,
		List<RoleRegistry> roleRegistries
	) {
		if (queryCommand.toUpperCase().equals("SELECT")) {
			Map<String, List<String>> forbiddenFieldsByTables = getUnionOfForbiddenFieldsByTables(roleRegistries);
			return Mono.just(!queryAccessForbiddenFields(accessFieldsByTables, forbiddenFieldsByTables));
		} else {
			List<String> unionForbiddenTables = getUnionOfForbiddenTables(roleRegistries, queryCommand);
			// return true if query doesn't access forbidden table
			return Mono.just(Collections.disjoint(accessTables, unionForbiddenTables));
		}
	}
	
	private boolean queryAccessForbiddenFields(
		Map<String, List<String>> accessFieldsByTables,
		Map<String, List<String>> forbiddenFieldsByTables
	) {
		boolean res = accessFieldsByTables
			.keySet()
			.stream()
			.anyMatch(
				key -> {
					boolean match = forbiddenFieldsByTables.containsKey(key) &&
						!Collections.disjoint(
							accessFieldsByTables.get(key),
							forbiddenFieldsByTables.get(key)
						);
					
					return match;
				}
			);
		
		return res;
	}
	
	private Map<String, List<String>> getUnionOfForbiddenFieldsByTables(List<RoleRegistry> roleRegistries) {
		Map<String, List<String>> res = roleRegistries
			.stream()
			.flatMap(
				roleRegistry -> roleRegistry
					.getSqlRestrictionModel()
					.getAccessForbiddenFieldByTable()
					.entrySet()
					.stream()
			)
			.collect(
				Collectors.toMap(
					Map.Entry::getKey,
					Map.Entry::getValue,
					(key1, key2) -> Stream
						.concat(key1.stream(), key2.stream())
						.distinct()
						.collect(Collectors.toList()),
					HashMap::new
				)
			);
		
		return res;
	}
	
	private List<String> getUnionOfForbiddenTables(List<RoleRegistry> roleRegistries, String queryCommand) {
		List<String> forbiddenTables = roleRegistries
			.stream()
			.flatMap(
				roleRegistry -> roleRegistry
					.getSqlRestrictionModel()
					.getForbiddenTableBySqlCommand(queryCommand)
					.stream()
			)
			.collect(Collectors.toList());
		
		int numOfRoles = roleRegistries.size();
		
		// if num of forbiddenTable appears same amount as num of roles, that means the table is
		// forbidden in every roles. which means it's union of forbidden tables
		return forbiddenTables
			.stream()
			.filter(forbiddenTable -> Collections.frequency(forbiddenTables, forbiddenTable) >= numOfRoles)
			.collect(Collectors.toList());
	}
}
