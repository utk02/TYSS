package com.te.flight.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.te.flight.entity.Flight;

public interface FlightRepository extends JpaRepository<Flight, String> {
	List<Flight> findByOrigin(String origin);
}
