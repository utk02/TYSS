package com.te.flight.service.implementation;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.te.flight.entity.Flight;
import com.te.flight.entity.User;
import com.te.flight.entity.dto.BookFlightDto;
import com.te.flight.entity.dto.FlightDetailsDto;
import com.te.flight.entity.dto.FlightDto;
import com.te.flight.entity.dto.SearchFlightDto;
import com.te.flight.exceptions.NoFlightFoundException;
import com.te.flight.exceptions.SeatNotAvailableException;
import com.te.flight.repository.FlightRepository;
import com.te.flight.repository.UserRepository;
import com.te.flight.service.FlightService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FlightServiceImplementation implements FlightService {
	private final FlightRepository flightRepository;
	private final UserRepository userRepository;

	@Override
	public FlightDto saveFlight(FlightDto flightDto) {
		Flight flight = new Flight();
		BeanUtils.copyProperties(flightDto, flight);
		Duration duration = Duration.between(flight.getDeparture(), flight.getArrival());
		String journeyTime = duration.toHours() + ":" + duration.toMinutes() % 60 + "hrs";
		flight.setJourneyTime(journeyTime);
		flight.setAvailableSeats(flight.getTotalSeats());
		flight.setBookedSeats(0);
		flight = flightRepository.save(flight);
		BeanUtils.copyProperties(flightDto, flight);
		return flightDto;
	}

	@Override
	public List<FlightDetailsDto> getFlightDetails(SearchFlightDto searchFlightDto) {
		if (!flightRepository.findByOrigin(searchFlightDto.getOrigin()).isEmpty()) {
			List<FlightDetailsDto> flightDetailsDtos = flightRepository.findByOrigin(searchFlightDto.getOrigin())
					.stream()
					.filter(f -> f.getDestination().equalsIgnoreCase(searchFlightDto.getDestination())
							&& f.getDeparture().getYear() == searchFlightDto.getDepartureDate().getYear()
							&& f.getDeparture().getMonth() == searchFlightDto.getDepartureDate().getMonth()
							&& f.getDeparture().getDayOfMonth() == searchFlightDto.getDepartureDate().getDayOfMonth()
							&& f.getAvailableSeats() > 0)
					.map(f -> {
						FlightDetailsDto detailsDto = new FlightDetailsDto();
						BeanUtils.copyProperties(f, detailsDto);
						return detailsDto;
					}).collect(Collectors.toList());
			if (!flightDetailsDtos.isEmpty()) {
				return flightDetailsDtos;
			}
			throw new NoFlightFoundException("No flights found for this journey");
		}
		throw new NoFlightFoundException("No Flights are scheduled from given origin");
	}

	@Override
	public BookFlightDto bookFlight(BookFlightDto bookFlightDto) {
		Flight flight = flightRepository.findById(bookFlightDto.getFlightId()).get();

		if (flight.getAvailableSeats() > 0) {
			flight.setBookedSeats(flight.getBookedSeats() + 1);
			flight.setAvailableSeats(flight.getTotalSeats() - flight.getBookedSeats());
			List<User> users = flight.getUsers();
			users.add(userRepository.findById(bookFlightDto.getUserId()).get());
			flight.setUsers(users);
			flight = flightRepository.save(flight);
			BeanUtils.copyProperties(flight, bookFlightDto);
			return bookFlightDto;
		}
		throw new SeatNotAvailableException("Seat not available");
	}

}
