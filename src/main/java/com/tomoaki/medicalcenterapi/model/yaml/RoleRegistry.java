package com.tomoaki.medicalcenterapi.model.yaml;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RoleRegistry {
	
	@JsonProperty("role")
	private String role;
	
	@JsonProperty("description")
	private String description;
	
	@JsonProperty("sqlRestrictionModel")
	private SqlRestrictionModel sqlRestrictionModel;
	
	public RoleRegistry() {
	
	}
	
	public RoleRegistry(String role, String description,
		SqlRestrictionModel sqlRestrictionModel) {
		this.role = role;
		this.description = description;
		this.sqlRestrictionModel = sqlRestrictionModel;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public SqlRestrictionModel getSqlRestrictionModel() {
		return sqlRestrictionModel;
	}
	
	public void setSqlRestrictionModel(
		SqlRestrictionModel sqlRestrictionModel) {
		this.sqlRestrictionModel = sqlRestrictionModel;
	}
}
