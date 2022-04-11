package com.tomoaki.medicalcenterapi.service;

import com.tomoaki.medicalcenterapi.model.entity.Problem;
import java.util.List;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StatsService {
	
	public Mono<Boolean> saveAllProblems(List<Problem> problems) {
		return null;
	}
}
