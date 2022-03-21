package com.tomoaki.medicalcenterapi.configuration;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

@Configuration
@EnableR2dbcRepositories
public class DatabaseConfiguration extends AbstractR2dbcConfiguration {
	
	@Override
	@Bean
	public ConnectionFactory connectionFactory() {
		ConnectionFactory connectionFactory = ConnectionFactories.get(
			"r2dbcs:mysql://root:10183284@127.0.0.1:3306/pomodoro"
		);
		
		return connectionFactory;
	}
}
