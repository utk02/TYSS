package com.te.flight.service.implementation;

import java.io.FileNotFoundException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.te.flight.entity.Flight;
import com.te.flight.entity.User;
import com.te.flight.entity.dto.BookFlightDto;
import com.te.flight.entity.dto.FlightDetailsDto;
import com.te.flight.entity.dto.FlightDto;
import com.te.flight.entity.dto.SearchFlightDto;
import com.te.flight.exceptions.InvalidUserIdException;
import com.te.flight.exceptions.NoFlightFoundException;
import com.te.flight.exceptions.SeatNotAvailableException;
import com.te.flight.repository.FlightRepository;
import com.te.flight.repository.UserRepository;
import com.te.flight.service.FlightService;
import com.te.flight.utils.EmailUtils;
import com.te.flight.utils.InvoiceUtils;
import com.te.flight.utils.SmsUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class FlightServiceImplementation implements FlightService {
	private final FlightRepository flightRepository;
	private final UserRepository userRepository;
	private final EmailUtils emailUtils;
	private final SmsUtils smsUtils;

	@Override
	public FlightDto saveFlight(FlightDto flightDto) {
		log.trace("In the service layer to save flight : " + flightDto.getFlightId());
		Flight flight = new Flight();
		BeanUtils.copyProperties(flightDto, flight);
		log.trace("converted from flightDto to flight");
		Duration duration = Duration.between(flight.getDeparture(), flight.getArrival());
		String journeyTime = duration.toHours() + ":" + duration.toMinutes() % 60 + "hrs";
		flight.setJourneyTime(journeyTime);
		flight.setAvailableSeats(flight.getTotalSeats());
		flight.setBookedSeats(0);
		log.debug("Saving flight");
		flight = flightRepository.save(flight);
		log.warn("flight saved flightId : " + flight.getFlightId() + " from : " + flight.getOrigin() + " to : "
				+ flight.getDestination());
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
		Optional<Flight> flightOptional = flightRepository.findById(bookFlightDto.getFlightId());
		Optional<User> userOptional = userRepository.findById(bookFlightDto.getUserId());
		if (flightOptional.isPresent() && userOptional.isPresent()) {
			Flight flight = flightOptional.get();
			User user = userOptional.get();

			if (flight.getAvailableSeats() > 0) {
				flight.setBookedSeats(flight.getBookedSeats() + 1);
				flight.setAvailableSeats(flight.getTotalSeats() - flight.getBookedSeats());
				List<User> users = flight.getUsers();
				users.add(user);
				flight.setUsers(users);
				flight = flightRepository.save(flight);
				try {
					InvoiceUtils.genrateInvoice(user, flight);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				emailUtils.sendEmail(user.getUserEmail(), "Flight booking confirmation", user, flight);
//				smsUtils.sendSms(user,flight);
				BeanUtils.copyProperties(flight, bookFlightDto);
				return bookFlightDto;
			}
			throw new SeatNotAvailableException("Seat not available");
		}
		throw new InvalidUserIdException("User with given user Id not found");
	}

}
