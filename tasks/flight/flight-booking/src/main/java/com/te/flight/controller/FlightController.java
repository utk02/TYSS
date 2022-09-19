package com.te.flight.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.te.flight.entity.dto.BookFlightDto;
import com.te.flight.entity.dto.FlightDetailsDto;
import com.te.flight.entity.dto.FlightDto;
import com.te.flight.entity.dto.SearchFlightDto;
import com.te.flight.response.GeneralResponse;
import com.te.flight.service.FlightService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FlightController {
	private final FlightService flightService;

	@PostMapping("/schedule")
	public ResponseEntity<GeneralResponse> saveFlight(@RequestBody FlightDto flightDto) {
		flightDto = flightService.saveFlight(flightDto);
		return ResponseEntity.ok()
				.body(new GeneralResponse(HttpStatus.OK, null, "Flight scheduled succesfully", flightDto));
	}

	@GetMapping("/flights")
	public ResponseEntity<GeneralResponse> getAvailableFlights(@RequestBody SearchFlightDto flightDto) {
		List<FlightDetailsDto> flightDetailsDtos = flightService.getFlightDetails(flightDto);
		return ResponseEntity.ok()
				.body(new GeneralResponse(HttpStatus.OK, null, "List of available flights", flightDetailsDtos));
	}

	@PostMapping("/book")
	public ResponseEntity<GeneralResponse> bookFlight(@RequestBody BookFlightDto bookFlightDto) {
		bookFlightDto = flightService.bookFlight(bookFlightDto);
		return ResponseEntity.ok().body(new GeneralResponse(HttpStatus.OK, null, "Flight booked", bookFlightDto));
	}

}
