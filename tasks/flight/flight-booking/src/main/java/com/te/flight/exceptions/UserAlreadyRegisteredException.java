package com.te.flight.exceptions;

public class UserAlreadyRegisteredException extends RuntimeException {

	public UserAlreadyRegisteredException(String message) {
		super(message);
	}

}
