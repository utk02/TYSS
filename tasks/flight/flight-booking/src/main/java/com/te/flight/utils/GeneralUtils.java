package com.te.flight.utils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.time.LocalDateTime;

import com.te.flight.entity.Flight;

public class GeneralUtils {
	public static String journeyDetailsGenerator(Flight flight) {
		LocalDateTime departure = flight.getDeparture();
		LocalDateTime arrival = flight.getArrival();

		return "From : " + flight.getOrigin() + "\nTo : " + flight.getDestination() + "\nDeparture Date : "
				+ departure.getDayOfMonth() + "/" + departure.getMonthValue() + "/" + departure.getYear()
				+ "\nDeparture Time : " + departure.getHour() + ":" + departure.getMinute() + "\nArrival Date : "
				+ arrival.getDayOfMonth() + "/" + arrival.getMonthValue() + "/" + arrival.getYear()
				+ "\nArrival Time : " + arrival.getHour() + ":" + arrival.getMinute() + "\nJourney Time : "
				+ flight.getJourneyTime();
	}

	public static String detailedPriceGenerator(Flight flight) {
		return BigDecimal.valueOf(flight.getPrice().floatValue() * 100 / 118).round(new MathContext(2))
				+ "\n  SGST : + 9% \n  CGST : + 9% ";
	}
}
