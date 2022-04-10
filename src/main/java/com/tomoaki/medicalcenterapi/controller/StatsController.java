package com.tomoaki.medicalcenterapi.controller;

import com.tomoaki.medicalcenterapi.model.Problem;
import com.tomoaki.medicalcenterapi.model.enums.Difficulty;
import com.tomoaki.medicalcenterapi.model.enums.ProblemType;
import com.tomoaki.medicalcenterapi.service.StatsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/resource")
public class StatsController {
	
	private StatsService statsService;
	
	@Autowired
	public StatsController(StatsService statsService) {
		this.statsService = statsService;
	}
	
	@GetMapping("/problem-stats")
	public Mono<ResponseEntity<?>> getStats() {
		return Mono.just(
			ResponseEntity.ok(
				List.of(
					new Problem(1,"Two Sum", Difficulty.EASY, ProblemType.ALGORITHMS),
					new Problem(100,"Broken Calculator", Difficulty.EASY, ProblemType.ALGORITHMS),
					new Problem(145,"Longest Substring Without Repeating Characters", Difficulty.MEDIUM, ProblemType.ALGORITHMS),
					new Problem(671,"Merge k Sorted Lists", Difficulty.EASY, ProblemType.ALGORITHMS),
					new Problem(51,"Divide Two Integers", Difficulty.EASY, ProblemType.ALGORITHMS),
					new Problem(189,"Combination Sum II", Difficulty.HARD, ProblemType.ALGORITHMS),
					new Problem(901,"Jump Game II", Difficulty.MEDIUM, ProblemType.ALGORITHMS)
				)
			)
		);
	}
	
	@PostMapping("/add-problem-records")
	public Mono<ResponseEntity<?>> addProblemRecords(
		@RequestBody List<Problem> problems
	) {
		// todo
		Mono<Boolean> saved = statsService.saveAllProblems(problems);
		return null;
	}
}
