package com.service;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

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
	private ConcurrentHashMap<String, String> otpStore = new ConcurrentHashMap<>();

    public String generateOtp() {
        Random random = new Random();
        int otp = 1000 + random.nextInt(9000); 
        return String.valueOf(otp);
    }

    public void storeOtp(String email, String otp) {
        otpStore.put(email, otp);
    }

    public void sendOTPMail(String recEmail) {
        // Generate OTP
        String otp = generateOtp();

        // Store OTP
        storeOtp(recEmail, otp);  
        
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "465");
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.auth", "true");

        // Get Session
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("mevadak75@gmail.com", "imdbdrwapaxkjyfx");
            }
        });
        session.setDebug(true);

        // Compose and send message
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(recEmail));
            message.setSubject("Welcome to Zomato");
            message.setText("Your OTP is: " + otp);

            // Send message
            Transport.send(message);
            System.out.println("OTP sent successfully to " + recEmail);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    // Validate OTP
    public boolean validateOTP(String email, String otp) {
        String storedOtp = otpStore.get(email);  

        // Debugging log for OTP validation
        System.out.println("Stored OTP for " + email + ": " + storedOtp);
        System.out.println("Received OTP: " + otp);

        // Compare stored OTP with received OTP
        return otp.equals(storedOtp);
    }
}
