package com.tomoaki.medicalcenterapi.model.yaml;

public class RoleRegistry {
	private String role;
	private String description;
	private SqlCommandRestrictionModel sqlCommandRestrictionModel;
	
	public RoleRegistry() {
	
	}
	
	public RoleRegistry(String role, String description,
		SqlCommandRestrictionModel sqlCommandRestrictionModel) {
		this.role = role;
		this.description = description;
		this.sqlCommandRestrictionModel = sqlCommandRestrictionModel;
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
	
	public SqlCommandRestrictionModel getSqlCommandRestrictionModel() {
		return sqlCommandRestrictionModel;
	}
	
	public void setSqlCommandRestrictionModel(
		SqlCommandRestrictionModel sqlCommandRestrictionModel) {
		this.sqlCommandRestrictionModel = sqlCommandRestrictionModel;
	}
}
