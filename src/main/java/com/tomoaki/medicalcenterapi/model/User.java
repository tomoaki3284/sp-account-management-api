package com.tomoaki.medicalcenterapi.model;

import java.util.Date;

/**
 * Model for User stored in database
 *
 * @author tmitsuhashi9621
 * @since 3/14/2022
 */
public class User {
	private long id;
	private String username;
	private String email;
	private String password;
	private Date date;
	
	public User(long id, String username, String email, String password, Date date) {
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.date = date;
	}
	
	public User() {
	
	}
	
	public User(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
