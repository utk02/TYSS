package com.te.flight.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.te.flight.entity.dto.BookFlightDto;
import com.te.flight.entity.dto.FlightDetailsDto;
import com.te.flight.entity.dto.FlightDto;
import com.te.flight.entity.dto.SearchFlightDto;
import com.te.flight.response.GeneralResponse;

public class FlightControllerTest {

	private FlightController flightController = mock(FlightController.class);

	@Test
	public void saveFlight_test() {
		FlightDto flightDto = new FlightDto("flight001", "mumbai", "nagpur", LocalDateTime.of(2022, 10, 12, 10, 30),
				LocalDateTime.of(2022, 10, 12, 11, 30), null, 100, BigDecimal.valueOf(5000.0));
		ResponseEntity<GeneralResponse> responseEntity = new ResponseEntity<GeneralResponse>(GeneralResponse.builder()
				.data(flightDto).error(false).message("Flight saved succesfully").status(HttpStatus.CREATED).build(),
				HttpStatus.CREATED);
		when(flightController.saveFlight(flightDto)).thenReturn(responseEntity);

		ResponseEntity<GeneralResponse> response = flightController.saveFlight(flightDto);
		assertThat(response).isNotNull();
		assertEquals(responseEntity, response);
	}

	@Test
	public void getAvailableFlights_test() {
		SearchFlightDto searchFlightDto = new SearchFlightDto("mumbai", "nagpur", LocalDate.of(2022, 10, 12));
		ArrayList<FlightDetailsDto> flightDetailsDtos = new ArrayList<>();
		flightDetailsDtos
				.add(new FlightDetailsDto("flight001", "mumbai", "nagpur", LocalDateTime.of(2022, 10, 12, 10, 30),
						LocalDateTime.of(2022, 10, 12, 11, 30), null, BigDecimal.valueOf(5000.0)));
		flightDetailsDtos
				.add(new FlightDetailsDto("flight002", "mumbai", "nagpur", LocalDateTime.of(2022, 10, 12, 8, 30),
						LocalDateTime.of(2022, 10, 12, 9, 30), null, BigDecimal.valueOf(5000.0)));

		ResponseEntity<GeneralResponse> responseEntity = new ResponseEntity<GeneralResponse>(GeneralResponse.builder()
				.data(flightDetailsDtos).error(false).message("list of available flight").status(HttpStatus.OK).build(),
				HttpStatus.OK);
		when(flightController.getAvailableFlights(searchFlightDto)).thenReturn(responseEntity);
		ResponseEntity<GeneralResponse> response = flightController.getAvailableFlights(searchFlightDto);
		assertThat(response).isNotNull();
		assertEquals(responseEntity, response);
	}

	@Test
	public void bookFlight_test() {
		BookFlightDto bookFlightDto = new BookFlightDto("flight001", "user001", LocalDateTime.of(2022, 10, 12, 10, 30));
		ResponseEntity<GeneralResponse> responseEntity = new ResponseEntity<GeneralResponse>(
				GeneralResponse.builder().status(HttpStatus.CREATED).error(false).message("flight booked successfully")
						.data(bookFlightDto).build(),
				HttpStatus.CREATED);
		when(flightController.bookFlight(bookFlightDto)).thenReturn(responseEntity);

		ResponseEntity<GeneralResponse> response = flightController.bookFlight(bookFlightDto);
		assertThat(response).isNotNull();
		assertEquals(responseEntity, response);
	}

}
