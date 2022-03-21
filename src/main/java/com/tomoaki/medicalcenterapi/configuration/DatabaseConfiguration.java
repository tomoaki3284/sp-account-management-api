package com.tomoaki.medicalcenterapi.configuration;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories
public class DatabaseConfiguration extends AbstractR2dbcConfiguration {
	
	@Value("${spring.r2dbc.ip}")
	private String ip;
	
	@Value("${spring.r2dbc.port}")
	private String port;
	
	@Value("${spring.r2dbc.dbname}")
	private String dbName;
	
	@Value("${spring.r2dbc.username}")
	private String dbUsername;
	
	@Value("${spring.r2dbc.password}")
	private String password;
	
	
	
	@Override
	@Bean
	public ConnectionFactory connectionFactory() {
		String dbUrl =
			String.format("r2dbcs:mysql://%s:%s@%s:%s/%s", dbUsername, password, ip, port, dbName);
		
		ConnectionFactory connectionFactory = ConnectionFactories.get(dbUrl);
		
		return connectionFactory;
	}
}
