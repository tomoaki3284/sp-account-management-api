package com.tomoaki.medicalcenterapi.model.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("UserRole")
public class UserRolePair {
	
	@Id
	private Long userID;
	
	@Column
	private String role;
	
	public UserRolePair(Long userID, String role) {
		this.userID = userID;
		this.role = role;
	}
	
	public UserRolePair() {}
	
	public Long getUserID() {
		return userID;
	}
	
	public void setUserID(Long userID) {
		this.userID = userID;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
}
