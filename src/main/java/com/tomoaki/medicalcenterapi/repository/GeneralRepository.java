package com.tomoaki.medicalcenterapi.repository;

import io.r2dbc.spi.ConnectionFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class GeneralRepository {
	
	private static final Logger logger = LoggerFactory.getLogger(GeneralRepository.class);
	
	private ConnectionFactory connectionFactory;
	
	@Autowired
	public GeneralRepository(ConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}
	
	public Mono<List<HashMap<String, Object>>> executeQuery(String query, List<String> accessFields) {
		R2dbcEntityTemplate r2dbcEntityTemplate = new R2dbcEntityTemplate(connectionFactory);
		
		return r2dbcEntityTemplate
			.getDatabaseClient()
			.sql(query)
			.map(row -> accessFields
				.stream()
				.map(field -> {
					// todo: if access field doesn't exist, return error
					return Map.entry(field, row.get(field));
				})
				.collect(Collectors.toList())
				.stream()
				.collect(Collectors.toMap(
					Map.Entry::getKey,
					Map.Entry::getValue,
					(key1, key2) -> key2,
					HashMap::new
				)))
			.all()
			.collectList();
	}
}
