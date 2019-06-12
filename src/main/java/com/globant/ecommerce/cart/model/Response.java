package com.globant.ecommerce.cart.model;

public class Response {
	private String message;
	private String statusCode;
	private DataModel data;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public DataModel getData() {
		return data;
	}
	public void setData(DataModel data) {
		this.data = data;
	}
	
	
}
