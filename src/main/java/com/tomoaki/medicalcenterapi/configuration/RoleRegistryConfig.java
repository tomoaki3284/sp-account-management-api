package com.tomoaki.medicalcenterapi.configuration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomoaki.medicalcenterapi.model.yaml.RoleRegistry;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.yaml.snakeyaml.Yaml;

@Configuration
public class RoleRegistryConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleRegistryConfig.class);
	
	private final List<String> registeredApps;
	
	public RoleRegistryConfig(
		@Value("#{'${app.role-registries}'.split(',')}") List<String> registeredApps
	) {
		this.registeredApps = registeredApps;
	}
	
	@Bean
	public Map<String, List<RoleRegistry>> roleRegistriesByAppCode() {
		Map<String, List<RoleRegistry>> roleRegistriesByAppCode = new HashMap<>();
		
		registeredApps
			.forEach(appCode -> {
				final String registeredAppYamlFileName = appCode.toLowerCase() + ".yml";
				String json = "";
				try (InputStream is = new ClassPathResource(registeredAppYamlFileName).getInputStream()) {
					json = convertYamlToJson(is);
					List<RoleRegistry> roleRegistries = convertJsonToRoleRegistries(json);
					roleRegistriesByAppCode.put(appCode, roleRegistries);
				} catch (IOException e) {
					LOGGER.error("Yaml file associated with the appCode not found. AppCode {}", appCode, e);
					throw new RuntimeException();
				}
			});
		
		return roleRegistriesByAppCode;
	}
	
	public String convertYamlToJson(InputStream is) {
		Yaml yaml = new Yaml();
		Map<String, Object> map = yaml.load(is);
		JSONObject jsonObj = new JSONObject(map);
		return jsonObj.toString();
	}
	
	public List<RoleRegistry> convertJsonToRoleRegistries(String json) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		
		Map<String, List<RoleRegistry>> map = mapper.readValue(json, new TypeReference<>() {});
		return map.get("roleRegistry");
	}
}
