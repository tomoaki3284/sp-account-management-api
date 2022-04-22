package com.tomoaki.medicalcenterapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomoaki.medicalcenterapi.model.yaml.RoleRegistry;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource("classpath:test.properties")
public class AuthorityServiceTest {
	
	@Autowired
	private Map<String, List<RoleRegistry>> roleRegistriesByAppCode;
	
	@Autowired
	private AuthorityService authorityService;
	
	private static final String QUERY_COMMAND = "a";
	private static final String ACCESS_TABLES = "b";
	private static final String ACCESS_FIELDS_BY_TABLES = "c";
	private static final String EXPECTED_RESULT = "d";
	private static final String ROLE_REGISTRIES_IDX = "e";
	
	private RoleRegistry[] roleRegistryUsers = new RoleRegistry[2];
	JSONObject[] jsonArray = new JSONObject[4];
	
	void setup() {
		List<RoleRegistry> roleRegistries = null;
		
		for (String key : roleRegistriesByAppCode.keySet()) {
			roleRegistries = roleRegistriesByAppCode.get(key);
		}
		
		this.roleRegistryUsers[0] = roleRegistries.get(0);
		this.roleRegistryUsers[1] = roleRegistries.get(1);
		
		// test object for SELECT command
		jsonArray[0] = new JSONObject(
			Map.of(
				QUERY_COMMAND, "SELECT",
				ACCESS_TABLES, List.of("User"),
				ACCESS_FIELDS_BY_TABLES, Map.of(
					"User", List.of("User_ID", "Email", "Username", "password", "Creation_Date")
				),
				EXPECTED_RESULT, true,
				ROLE_REGISTRIES_IDX, 0
			)
		);
		
		jsonArray[1] = new JSONObject(
			Map.of(
				QUERY_COMMAND, "SELECT",
				ACCESS_TABLES, List.of("User"),
				ACCESS_FIELDS_BY_TABLES, Map.of(
					"User", List.of("User_ID", "Email", "Username", "password", "Creation_Date")
				),
				EXPECTED_RESULT, false,
				ROLE_REGISTRIES_IDX, 1
			)
		);
		
		// test object for DELETE command
		jsonArray[2] = new JSONObject(
			Map.of(
				QUERY_COMMAND, "DELETE",
				ACCESS_TABLES, List.of("problem"),
				ACCESS_FIELDS_BY_TABLES, Map.of(),
				EXPECTED_RESULT, false,
				ROLE_REGISTRIES_IDX, 0
			)
		);
		
		jsonArray[3] = new JSONObject(
			Map.of(
				QUERY_COMMAND, "DELETE",
				ACCESS_TABLES, List.of("problem"),
				ACCESS_FIELDS_BY_TABLES, Map.of(),
				EXPECTED_RESULT, true,
				ROLE_REGISTRIES_IDX, 1
			)
		);
	}
	
	@Test
	void shouldValidateAccessForbiddenTablesCorrectly()
		throws JSONException, JsonProcessingException {
		setup();
		
		ObjectMapper mapper = new ObjectMapper();
		for (JSONObject jsonObject : jsonArray) {
			String queryCommand = (String) jsonObject.get(QUERY_COMMAND);
			List<String> accessTables = mapper.readValue(jsonObject.get(ACCESS_TABLES).toString(), new TypeReference<List<String>>() {});
			Map<String,List<String>> accessFieldsByTable = mapper.readValue(jsonObject.get(ACCESS_FIELDS_BY_TABLES).toString(), new TypeReference<Map<String, List<String>>>() {});
			int roleRegistriesIdx = (int) jsonObject.get(ROLE_REGISTRIES_IDX);
			
			StepVerifier
				.create(
					authorityService
						.queryNotAccessForbiddenTables(
							queryCommand, accessTables, accessFieldsByTable, List.of(roleRegistryUsers[roleRegistriesIdx])
						)
				)
				.assertNext(actualResponse -> {
					try {
						assertEquals(jsonObject.get(EXPECTED_RESULT), actualResponse);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				})
				.verifyComplete();
		}
		
		
	}
}
