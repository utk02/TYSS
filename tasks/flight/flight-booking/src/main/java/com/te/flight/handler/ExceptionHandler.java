package com.te.flight.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import com.te.flight.exceptions.InvalidUserIdException;
import com.te.flight.exceptions.NoFlightFoundException;
import com.te.flight.exceptions.SeatNotAvailableException;
import com.te.flight.response.GeneralResponse;

@ControllerAdvice
public class ExceptionHandler {

	@org.springframework.web.bind.annotation.ExceptionHandler
	public ResponseEntity<GeneralResponse> handler(NoFlightFoundException e) {
		return new ResponseEntity<GeneralResponse>(GeneralResponse.builder().status(HttpStatus.NOT_FOUND).error(true)
				.message("No flight found for given journey").build(), HttpStatus.NOT_FOUND);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler
	public ResponseEntity<GeneralResponse> handler(SeatNotAvailableException e) {
		return ResponseEntity.badRequest()
				.body(new GeneralResponse(HttpStatus.BAD_REQUEST, true, e.getMessage(), null));
	}

	@org.springframework.web.bind.annotation.ExceptionHandler
	public ResponseEntity<GeneralResponse> handler(InvalidUserIdException e) {
		return ResponseEntity.badRequest()
				.body(new GeneralResponse(HttpStatus.BAD_REQUEST, true, e.getMessage(), null));
	}

}
