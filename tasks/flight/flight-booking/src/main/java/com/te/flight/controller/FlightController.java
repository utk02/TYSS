package com.te.flight.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.te.flight.entity.dto.BookFlightDto;
import com.te.flight.entity.dto.FlightDetailsDto;
import com.te.flight.entity.dto.FlightDto;
import com.te.flight.entity.dto.SearchFlightDto;
import com.te.flight.response.GeneralResponse;
import com.te.flight.service.FlightService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/flight")
@RequiredArgsConstructor
public class FlightController {
	private final FlightService flightService;

	@PostMapping("/schedule")
	public ResponseEntity<GeneralResponse> saveFlight(@RequestBody FlightDto flightDto) {
		log.trace("In the flight controller -> saveFlight api");
		flightDto = flightService.saveFlight(flightDto);
		log.debug("New Flight scheduled with flight id : " + flightDto.getFlightId());
//		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/flight").toUriString());
//		return ResponseEntity.created(uri)
//				.body(new GeneralResponse(HttpStatus.OK, false, "Flight scheduled succesfully", flightDto));
		return new ResponseEntity<GeneralResponse>(GeneralResponse.builder().data(flightDto).error(false)
				.message("Flight saved succesfully").status(HttpStatus.CREATED).build(), HttpStatus.CREATED);
	}

	@PostMapping("/flights")
	public ResponseEntity<GeneralResponse> getAvailableFlights(@RequestBody SearchFlightDto flightDto) {
		log.trace("In the flight controller -> getAvailableFlights api , searching flight : " + flightDto);
		List<FlightDetailsDto> flightDetailsDtos = flightService.getFlightDetails(flightDto);
//		return ResponseEntity.ok()
//				.body(new GeneralResponse(HttpStatus.CREATED, false, "List of available flights", flightDetailsDtos));
		return new ResponseEntity<GeneralResponse>(GeneralResponse.builder().data(flightDetailsDtos).error(false)
				.message("list of available flight").status(HttpStatus.OK).build(), HttpStatus.OK);
	}

	@PostMapping("/book")
	public ResponseEntity<GeneralResponse> bookFlight(@RequestBody BookFlightDto bookFlightDto) {
		log.trace("In the flight controller -> bookFlight api userId : " + bookFlightDto.getUserId() + " flightId : "
				+ bookFlightDto.getFlightId());
		bookFlightDto = flightService.bookFlight(bookFlightDto);
		log.warn("Flight booked with userId : " + bookFlightDto.getUserId() + " flightId : "
				+ bookFlightDto.getFlightId());
//		URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/flight").toUriString());
//		return ResponseEntity.created(uri)
//				.body(new GeneralResponse(HttpStatus.CREATED, false, "Flight booked", bookFlightDto));
		return new ResponseEntity<GeneralResponse>(GeneralResponse.builder().status(HttpStatus.CREATED).error(false)
				.message("flight booked successfully").data(bookFlightDto).build(), HttpStatus.CREATED);
	}

}
