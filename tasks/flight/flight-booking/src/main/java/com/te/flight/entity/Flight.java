package com.te.flight.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Flight {
	@Id
	private String flightId;

	private String origin;
	private String destination;
	private LocalDateTime departure;
	private LocalDateTime arrival;
	private String journeyTime;
	private Integer totalSeats;
	private Integer bookedSeats;
	private Integer availableSeats;
	private BigDecimal price;
	
	@OneToMany(cascade = CascadeType.ALL)
	private List<User> users = new ArrayList<>();

}
