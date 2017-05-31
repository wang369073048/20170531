package com.asdc.lrm.jersey;

public class ServiceResponse {

	private int code;  					//200,400
	private String status;				//success,failed
	private String message;
	
	public int getCode() {
		return code;
	}
	public ServiceResponse setCode(int code) {
		this.code = code;
		return this;
	}
	public String getStatus() {
		return status;
	}
	public ServiceResponse setStatus(String status) {
		this.status = status;
		return this;
	}
	public String getMessage() {
		return message;
	}
	public ServiceResponse setMessage(String message) {
		this.message = message;
		return this;
	}
	
	public ServiceResponse(int code,String status,String message){
		this.code = code;
		this.status = status;
		this.message = message;
	}
	
	public ServiceResponse(){}
	@Override
	public String toString() {
		return "code=" + code + ", status=" + status
				+ ", message=" + message;
	}

	
	
	
}
