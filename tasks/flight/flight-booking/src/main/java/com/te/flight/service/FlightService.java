package com.te.flight.service;

import java.util.List;

import com.te.flight.entity.dto.BookFlightDto;
import com.te.flight.entity.dto.FlightDetailsDto;
import com.te.flight.entity.dto.FlightDto;
import com.te.flight.entity.dto.SearchFlightDto;

public interface FlightService {
	public FlightDto saveFlight(FlightDto flightDto);
	
	public List<FlightDetailsDto> getFlightDetails(SearchFlightDto searchFlightDto);
	
	public BookFlightDto bookFlight(BookFlightDto bookFlightDto);
}
