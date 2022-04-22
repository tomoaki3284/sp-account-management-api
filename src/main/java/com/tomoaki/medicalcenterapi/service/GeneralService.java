package com.tomoaki.medicalcenterapi.service;

import com.tomoaki.medicalcenterapi.repository.GeneralRepository;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class GeneralService {
	
	private static final Logger logger = LoggerFactory.getLogger(GeneralService.class);
	
	private GeneralRepository generalRepository;
	
	@Autowired
	public GeneralService(GeneralRepository generalRepository) {
		this.generalRepository = generalRepository;
	}
	
	public Mono executeQuery(
		String query,
		String queryCommand,
		Map<String, List<String>> accessFieldsByTable
	) {
		if (queryCommand.equals("SELECT")) {
			// get all access fields as list
			final List<String> accessFields = accessFieldsByTable
				.values()
				.stream()
				.flatMap(Collection::stream)
				.collect(Collectors.toUnmodifiableList());
			
			return generalRepository.executeSelectQuery(query, accessFields);
		} else {
			return generalRepository
				.executeModificationQuery(query)
				.map(affectedRows -> Map.of("success", affectedRows <= 1));
		}
		
	}
}
