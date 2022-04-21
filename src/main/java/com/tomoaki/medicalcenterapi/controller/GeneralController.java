package com.tomoaki.medicalcenterapi.controller;

import com.tomoaki.medicalcenterapi.model.UserDetailsImpl;
import com.tomoaki.medicalcenterapi.service.AuthorityService;
import com.tomoaki.medicalcenterapi.service.GeneralService;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@Controller
@RestController
@RequestMapping("/resource")
public class GeneralController {
	
	private static final Logger logger = LoggerFactory.getLogger(GeneralController.class);
	
	private AuthorityService authorityService;
	
	private GeneralService generalService;
	
	@Autowired
	public GeneralController(AuthorityService authorityService,
		GeneralService generalService) {
		this.authorityService = authorityService;
		this.generalService = generalService;
	}
	
	@RequestMapping("/query/{appCode}")
	public Mono<ResponseEntity<Object>> customQuery(
		@PathVariable String appCode,
		@RequestBody Map<String,Object> requestBody,
		Authentication authentication
	) {
		String query = (String) requestBody.get("query");
		String queryCommand = (String) requestBody.get("queryCommand");
		List<String> accessTables = (List) requestBody.get("accessTables");
		Map<String, List<String>> accessFieldsByTable = (Map) requestBody.get("accessFieldsByTable");
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		
		Mono<ResponseEntity<Object>> response = authorityService
			.getRoleRegistriesById(userDetails.getId(), appCode)
			.flatMap(roleRegistries -> {
				return authorityService
					.queryNotAccessForbiddenTables(
						queryCommand,
						accessTables,
						accessFieldsByTable,
						roleRegistries
					);
			})
			.flatMap(success -> {
				return success ? Mono.just(success) : Mono.empty();
			})
			.flatMap(a -> {
				return generalService
					.executeQuery(query, queryCommand, accessFieldsByTable)
					.flatMap(res -> {
						return Mono.just(createEntity(HttpStatus.OK, res));
					});
			})
			.switchIfEmpty(Mono.defer(() -> Mono.just(createEntity(HttpStatus.FORBIDDEN, null))));
		
		return response;
	}
	
	private ResponseEntity<Object> createEntity(HttpStatus status, Object result) {
		return result != null ? ResponseEntity.status(status).body(result) :
			ResponseEntity.status(status).build();
	}
}
