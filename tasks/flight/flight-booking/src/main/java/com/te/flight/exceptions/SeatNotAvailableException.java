package com.te.flight.exceptions;

public class SeatNotAvailableException extends RuntimeException {
	public SeatNotAvailableException(String message) {
		super(message);
	}
}
