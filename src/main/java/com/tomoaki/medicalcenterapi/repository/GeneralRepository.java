package com.tomoaki.medicalcenterapi.repository;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public class GeneralRepository {
	
	private DatabaseClient databaseClient;
	
	@Autowired
	public GeneralRepository(DatabaseClient databaseClient) {
		this.databaseClient = databaseClient;
	}
	
	public Flux<Map<String, Object>> executeQuery(String query) {
		return databaseClient
			.sql(query)
			.fetch()
			.all();
	}
}
