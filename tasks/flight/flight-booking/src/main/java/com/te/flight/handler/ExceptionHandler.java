package com.te.flight.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.te.flight.exceptions.FlightIdAlreadyExistException;
import com.te.flight.exceptions.InvalidUserIdException;
import com.te.flight.exceptions.NoFlightFoundException;
import com.te.flight.exceptions.SeatNotAvailableException;
import com.te.flight.exceptions.SomethingWentWrongException;
import com.te.flight.exceptions.UserAlreadyRegisteredException;
import com.te.flight.response.GeneralResponse;

@ControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler
	public ResponseEntity<GeneralResponse> handler(NoFlightFoundException e) {
		return new ResponseEntity<>(
				GeneralResponse.builder().status(HttpStatus.NO_CONTENT).error(true).message(e.getMessage()).build(),
				HttpStatus.NO_CONTENT);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler
	public ResponseEntity<GeneralResponse> handler(SeatNotAvailableException e) {
		return new ResponseEntity<>(
				GeneralResponse.builder().status(HttpStatus.NO_CONTENT).error(true).message(e.getMessage()).build(),
				HttpStatus.NO_CONTENT);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler
	public ResponseEntity<GeneralResponse> handler(InvalidUserIdException e) {
		return new ResponseEntity<>(GeneralResponse.builder().status(HttpStatus.UNPROCESSABLE_ENTITY).error(true)
				.message(e.getMessage()).build(), HttpStatus.UNPROCESSABLE_ENTITY);
	}

	public ResponseEntity<GeneralResponse> handler(FlightIdAlreadyExistException e) {
		return new ResponseEntity<>(GeneralResponse.builder().status(HttpStatus.RESET_CONTENT).error(true)
				.message("flight with requested flightId already exists, use different flightId and try again").build(),
				HttpStatus.RESET_CONTENT);
	}

	public ResponseEntity<GeneralResponse> handler(SomethingWentWrongException e) {
		return new ResponseEntity<>(GeneralResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).error(true)
				.message("something went wrong please try again").build(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public ResponseEntity<GeneralResponse> handler(UserAlreadyRegisteredException e) {
		return new ResponseEntity<>(GeneralResponse.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).error(true)
				.message(e.getMessage()).build(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
