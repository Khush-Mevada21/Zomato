package com.service;

import java.util.Properties;

import org.springframework.stereotype.Service;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService 
{
	
	public void sendOTPMail(String recEmail, String msg) 
	{
		Properties props = System.getProperties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "465");
		props.put("mail.smtp.ssl.enable", "true");
		props.put("mail.smtp.auth", "true");

		// get Session
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication("mevadak75@gmail.com", "imdbdrwapaxkjyfx");
			}
		});
		session.setDebug(true);
		// compose message
		try {
			MimeMessage message = new MimeMessage(session);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(recEmail));
			message.setSubject("Welcome to Zomato");
			message.setText(msg);
			
			// send message
			Transport.send(message);
			System.out.println("otp sent successfully");
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
	
	
}
