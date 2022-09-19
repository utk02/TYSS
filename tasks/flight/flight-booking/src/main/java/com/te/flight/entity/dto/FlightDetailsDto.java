package com.te.flight.entity.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FlightDetailsDto {

	private String flightId;
	private String origin;
	private String destination;
	private LocalDateTime departure;
	private LocalDateTime arrival;
	private String journeyTime;
	private BigDecimal price;
}
