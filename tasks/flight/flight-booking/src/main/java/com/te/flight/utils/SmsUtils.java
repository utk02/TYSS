package com.te.flight.utils;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.te.flight.entity.Flight;
import com.te.flight.entity.User;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SmsUtils {
	@Value("${ACCOUNT_SID}")
	private String ACCOUNT_SID;
	@Value("${AUTH_TOKEN}")
	private String AUTH_TOKEN;
	@Value("${FROM_NUMBER}")
	private String FROM_NUMBER;
	@Value("${TO_NUMBER}")
	private String TO_NUMBER;

	public void sendSms(User user, Flight flight) {
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		log.trace("Sending...");
		Message message = Message
				.creator(new PhoneNumber(TO_NUMBER), new PhoneNumber(FROM_NUMBER), composeSms(user, flight)).create();

		log.trace(message.getSid());
		log.trace("sent");
	}

	public String composeSms(User user, Flight flight) {
		LocalDateTime departure = flight.getDeparture();
		return "Dear " + user.getUsername() + ", Booking confirmed on " + departure.getDayOfMonth() + "/"
				+ departure.getMonthValue() + "/" + departure.getYear() + " at " + departure.getHour() + ":"
				+ departure.getMinute() + "hrs" + " flight id : " + flight.getFlightId();
	}
}
