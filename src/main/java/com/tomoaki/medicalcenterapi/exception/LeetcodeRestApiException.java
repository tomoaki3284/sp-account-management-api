package com.tomoaki.medicalcenterapi.exception;

public class LeetcodeRestApiException extends Exception {
	
	public LeetcodeRestApiException() {
		super();
	}
	
	public LeetcodeRestApiException(String message) {
		super(message);
	}
	
	public LeetcodeRestApiException(String message, Throwable cause) {
		super(message, cause);
	}
}
