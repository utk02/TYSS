package com.te.flight.utils;

import java.time.LocalDateTime;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.te.flight.entity.Flight;
import com.te.flight.entity.User;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class EmailUtils {
	@Value("${EMAIL_ID}")
	private String EMAIL_ID;
	@Value("${PASSWORD}")
	private String PASSWORD;
	@Value("${HOST}")
	private String HOST;
	@Value("${PORT}")
	private String PORT;

	public void sendEmail(String emailAddress, String subject, User user, Flight flight) {

		log.trace("sending email ...");
		log.debug("from emailId : " + EMAIL_ID + " host : " + HOST + " port " + PORT);
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", HOST);
		properties.setProperty("mail.smtp.port", PORT);
		properties.setProperty("mail.smtp.ssl.enable", "true");
		properties.setProperty("mail.smtp.auth", "true");

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(EMAIL_ID, PASSWORD);
			}
		});

//		session.setDebug(true);

		MimeMessage mimeMessage = new MimeMessage(session);
		try {
			mimeMessage.setFrom(new InternetAddress(EMAIL_ID));
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
			mimeMessage.setSubject(subject);

			MimeMultipart mimeMultipart = new MimeMultipart();
			MimeBodyPart attachmentPart = new MimeBodyPart();
			MimeBodyPart textPart = new MimeBodyPart();
			log.trace("composing email");
			textPart.setText(composeEmail(user, flight));
			log.info("email composed");
			String file = "G:\\Workspace\\tasks\\Invoices\\" + user.getUsername() + "Invoice.pdf";
			String fileName = "booking_invoice.pdf";

			log.trace("adding attachment to email ...");

			FileDataSource fileDataSource = new FileDataSource(file);
			attachmentPart.setDataHandler(new DataHandler(fileDataSource));
			attachmentPart.setFileName(fileName);

			mimeMultipart.addBodyPart(textPart);
			mimeMultipart.addBodyPart(attachmentPart);
			mimeMessage.setContent(mimeMultipart);

			log.info("attachment added");

			Transport.send(mimeMessage);
			log.info("email sent");
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public String composeEmail(User user, Flight flight) {
		LocalDateTime departure = flight.getDeparture();

		return "Dear " + user.getUsername() + ",\nYour booking for flight " + flight.getFlightId() + " from "
				+ flight.getOrigin().toUpperCase() + " to " + flight.getDestination().toUpperCase() + " on "
				+ departure.getDayOfMonth() + " " + departure.getMonth() + " " + departure.getYear() + " at "
				+ departure.getHour() + ":" + departure.getMinute() + " hrs"
				+ " is confirmed.\nPlease find the Attachment for the incoice.\nWish you a very happy journey.\nThank you";
	}
}
