package com.tomoaki.medicalcenterapi.repository;

import com.tomoaki.medicalcenterapi.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * User repository for accessing data
 *
 * @author tmitsuhashi9621
 * @since 3/14/2022
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	
	/**
	 * Return false if username already exist in the database
	 *
	 * @param username
	 * @return
	 */
	Boolean existsByUsername(String username);
	
	/**
	 * Return false if email already exist in the database
	 *
	 * @param email
	 * @return
	 */
	Boolean existsByEmail(String email);
}
