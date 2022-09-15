package com.example.xpandit.dto;

public class Response {
	String message;
	Object objectResponse;
	
	public Response(String message, Object objectResponse) {
		this.message = message;
		this.objectResponse = objectResponse;
	}
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getObjectResponse() {
		return objectResponse;
	}
	public void setObjectResponse(Object objectResponse) {
		this.objectResponse = objectResponse;
	}
	

}
