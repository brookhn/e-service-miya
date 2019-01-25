package com.pp.server.inter;

public class UnableToAcquireLockException extends RuntimeException{
	public UnableToAcquireLockException() {
		
	}
	
	public UnableToAcquireLockException(String message) {
		super(message);
	}
	
	public UnableToAcquireLockException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
