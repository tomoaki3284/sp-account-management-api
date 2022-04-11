package com.tomoaki.medicalcenterapi.repository;

import com.tomoaki.medicalcenterapi.model.entity.User;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * User repository for accessing data
 *
 * @author tmitsuhashi9621
 * @since 3/14/2022
 */
@Repository
public interface UserRepository extends ReactiveCrudRepository<User, Long> {
	
	@Query("SELECT * from User where username = :username")
	Mono<User> findByUsername(String username);
	
	/**
	 * Return false if username already exist in the database
	 *
	 * @param username
	 * @return
	 */
	@Query("SELECT EXISTS(SELECT 1 FROM User WHERE username = :username)")
	Mono<Integer> existsByUsername(String username);
	
	/**
	 * Return false if email already exist in the database
	 *
	 * @param email
	 * @return
	 */
	@Query("SELECT EXISTS(SELECT 1 FROM User WHERE email = :email)")
	Mono<Integer> existsByEmail(String email);
}
