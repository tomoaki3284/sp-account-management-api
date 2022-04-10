package com.tomoaki.medicalcenterapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.time.LocalDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Model for User stored in database
 *
 * @author tmitsuhashi9621
 * @since 3/14/2022
 */
@Table("User")
public class User {
	
	@JsonIgnore
	@Id
	private long id;
	
	@Column
	private String username;
	
	@Column
	private String email;
	
	@Column
	private String password;
	
	@Column("creation_date")
	private LocalDate date;
	
	public User(long id, String username, String email, String password, LocalDate date) {
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
	
	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	public LocalDate getDate() {
		return date;
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
