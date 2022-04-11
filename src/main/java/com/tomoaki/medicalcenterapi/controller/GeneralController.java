package com.tomoaki.medicalcenterapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Controller
@RestController
@RequestMapping("/general")
public class GeneralController {
	
	@RequestMapping("/custom")
	public Mono<ResponseEntity<?>> customQuery() {
		return null;
	}
}
