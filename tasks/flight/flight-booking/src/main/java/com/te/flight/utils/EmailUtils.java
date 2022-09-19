package com.te.flight.utils;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailUtils {
	private static final String usename = "shubhamte36@gmail.com";
	private static final String password = "test12345#";
	private static String host = "smtp.gmail.com";
	private static String port = "465";
	private static final String appPassword = "vpwhuqwfaabcunzg";

	public static boolean sendEmail(String emailAddress, String subject, String message) {

		Properties properties = System.getProperties();
		properties.setProperty("host", host);
		properties.setProperty("port", port);
		properties.setProperty("mail.smtp.ssl.enable", "true");
		properties.setProperty("mail.smtp.auth", "true");

		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(usename, appPassword);
			}
		});

		MimeMessage mimeMessage = new MimeMessage(session);
		try {
			mimeMessage.setFrom(new InternetAddress(usename));
			mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(emailAddress));
			mimeMessage.setSubject(subject);
			mimeMessage.setText(message);

			Transport.send(mimeMessage);
			return true;
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return false;

	}
}
