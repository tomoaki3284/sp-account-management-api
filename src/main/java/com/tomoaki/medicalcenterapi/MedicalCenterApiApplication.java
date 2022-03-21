package com.tomoaki.medicalcenterapi;

import com.tomoaki.medicalcenterapi.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication(exclude = {R2dbcAutoConfiguration.class})
public class MedicalCenterApiApplication {
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
//	@Bean
//	public ConnectionFactoryInitializer initializer(ConnectionFactory connectionFactory) {
//		ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
//		initializer.setConnectionFactory(connectionFactory);
//		return initializer;
//	}

	public static void main(String[] args) {
		SpringApplication.run(MedicalCenterApiApplication.class, args);
	}
	
	@Bean
	CommandLineRunner start(UserRepository userRepository, PasswordEncoder passwordEncoder){
		// todo: this is just for debugging purpose, will be deleted
		return args -> {
			userRepository.findAll().log().subscribe(u -> log.info("user: {}", u.getUsername()));
		};
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
}
