package com.tomoaki.medicalcenterapi.repository;

import com.tomoaki.medicalcenterapi.model.entity.UserRolePair;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface AuthorityRepository extends ReactiveCrudRepository<UserRolePair, Long> {
	
	@Query("SELECT Role from RoleAssignment where User_ID = :userID")
	Flux<String> getRolesByUserID(Long userID);
}
