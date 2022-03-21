package com.tomoaki.medicalcenterapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/resource")
public class StatsController {
	
	@GetMapping("/stats")
	public Mono<ResponseEntity<?>> getStats() {
		return Mono.just(
			ResponseEntity.ok("success")
		);
	}
}
