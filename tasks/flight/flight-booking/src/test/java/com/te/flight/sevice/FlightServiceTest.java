package com.te.flight.sevice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;

import com.te.flight.entity.Flight;
import com.te.flight.entity.dto.BookFlightDto;
import com.te.flight.entity.dto.FlightDetailsDto;
import com.te.flight.entity.dto.FlightDto;
import com.te.flight.entity.dto.SearchFlightDto;
import com.te.flight.repository.FlightRepository;
import com.te.flight.service.FlightService;


public class FlightServiceTest {
	private FlightService flightService = mock(FlightService.class);
	@Mock
	private FlightRepository flightRepository;

	@Test
	public void saveFlight_test() {
		FlightDto flightDto = new FlightDto("flight001", "mumbai", "nagpur", LocalDateTime.of(2022, 10, 12, 10, 30),
				LocalDateTime.of(2022, 10, 12, 11, 30), null, 100, BigDecimal.valueOf(5000.0));
		when(flightService.saveFlight(flightDto)).thenReturn(flightDto);
		FlightDto saveFlight = flightService.saveFlight(flightDto);
		assertThat(saveFlight).isNotNull();
		assertEquals(flightDto, saveFlight);

	}

	@Test
	public void getFlightDetails_test() {
		SearchFlightDto searchFlightDto = new SearchFlightDto("mumbai", "nagpur", LocalDate.of(2022, 10, 12));
		ArrayList<FlightDetailsDto> flightDetailsDtos = new ArrayList<>();
		flightDetailsDtos
				.add(new FlightDetailsDto("flight001", "mumbai", "nagpur", LocalDateTime.of(2022, 10, 12, 10, 30),
						LocalDateTime.of(2022, 10, 12, 11, 30), null, BigDecimal.valueOf(5000.0)));
		flightDetailsDtos
				.add(new FlightDetailsDto("flight002", "mumbai", "nagpur", LocalDateTime.of(2022, 10, 12, 8, 30),
						LocalDateTime.of(2022, 10, 12, 9, 30), null, BigDecimal.valueOf(5000.0)));
		when(flightService.getFlightDetails(searchFlightDto)).thenReturn(flightDetailsDtos);
		List<FlightDetailsDto> flightDetails = flightService.getFlightDetails(searchFlightDto);
		assertThat(flightDetails).isNotNull();
		assertEquals(flightDetailsDtos, flightDetails);
	}

	@Test
	public void bookFlight_test() {
		BookFlightDto bookFlightDto = new BookFlightDto("flight001", "user001", LocalDateTime.of(2022, 10, 12, 10, 30));
		when(flightService.bookFlight(bookFlightDto)).thenReturn(bookFlightDto);
		BookFlightDto bookFlight = flightService.bookFlight(bookFlightDto);
		assertThat(bookFlight).isNotNull();
		assertEquals(bookFlightDto, bookFlight);
	}

}
