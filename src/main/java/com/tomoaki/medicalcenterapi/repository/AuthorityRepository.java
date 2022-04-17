package com.tomoaki.medicalcenterapi.repository;

import com.tomoaki.medicalcenterapi.model.entity.UserRolePair;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends ReactiveCrudRepository<UserRolePair, Long> {

}
