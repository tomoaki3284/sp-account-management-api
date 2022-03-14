package com.tomoaki.medicalcenterapi.model.request;

/**
 * Request model for login request from client
 *
 * @author tmitsuhashi9621
 * @since 3/14/2022
 */
public class LoginRequest {
	private String username;
	private String password;
	
	public LoginRequest(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
