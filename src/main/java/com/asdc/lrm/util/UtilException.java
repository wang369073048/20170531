package com.asdc.lrm.util;

public class UtilException extends RuntimeException {
	private static final long serialVersionUID = -7128497724286024457L;
	private String errorKey;

	public UtilException(String errorKey){
		super();
		this.errorKey = errorKey;
	}
	
	public UtilException(String errorKey, String message){
		super(message);
		this.errorKey = errorKey;
	}
	
	public UtilException(String errorKey, String message, Throwable cause){
		super(message, cause);
		this.errorKey = errorKey;
	}

	public String getErrorKey() {
		return errorKey;
	}
}