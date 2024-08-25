package com.validation.services;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.validation.dao.BookingRepo;
import com.validation.dao.PropertyRepo;
import com.validation.entities.Booking;
import com.validation.entities.Property;

import jakarta.validation.Valid;

@Service
public class BookingService {

	@Autowired
	private BookingRepo bookingRepo;

	@Autowired
	private PropertyRepo propertyRepo;

	@Autowired
	private JavaMailSender mailSender;
	
		@Value("$(spring.mail.username)")
		private String fromEmailId;

	public List<Property> findUnbookedProperties(LocalDate checkInDate, LocalDate checkOutDate) {

		List<Integer> unbookedPropertyIds = bookingRepo.findUnbookedPropertyIds(checkInDate, checkOutDate);

		// Fetch property details using the unbooked property IDs
		return propertyRepo.findAllById(unbookedPropertyIds);
	}

	public boolean processpayment(String cardNumber, String expiryDate, String cvv) {
		return true;
	}

	public void saveBooking(String email, String contact, String checkInDate,
	  String checkOutDate, String billingName, Integer propertyId ) {
	  
	  
	  LocalDate checkIn = LocalDate.parse(checkInDate);
	  LocalDate checkOut =
	  LocalDate.parse(checkOutDate);
	  
	  // Find the property by ID Property Property =
	 Property property= propertyRepo.findById(propertyId).orElse(null);
	  
	  // Create a new Booking object
	  Booking booking = new Booking(property, checkOut, checkOut); 
	  booking.setEmail(email); booking.setContact(contact);
	  booking.setCheckInDate(checkIn); booking.setCheckOutDate(checkOut);
	  booking.setBillingName(billingName); 
	  booking.setProperty(property);
	  bookingRepo.save(booking);
	  
	  // Save the booking to the database bookingRepo.save(booking);
	  
	  }

	public void sendConfirmationEmail(String email, String contact, String checkInDate, String checkOutDate, String agentContact, double price, String state, String billingName) {
	    SimpleMailMessage message = new SimpleMailMessage();
	    message.setTo(email);
	    message.setSubject("Booking Confirmation");

	    String emailContent = String.format(
	        "Dear %s,\n\n" +
	        "Your booking has been confirmed.\n\n" +
	        "Booking Details:\n" +
	        "Check-In Date: %s\n" +
	        "Check-Out Date: %s\n" +
	        "Contact: %s\n" +
	        "Agent Contact: %s\n" +
	        "Property Price: $%s\n" +
	        "Property State: %s\n\n" +
	        "Thank you for choosing us!\n\n" +
	        "Best regards,\n" +
	        "ELITESTAYS",
	        billingName, 
	        checkInDate.toString(), 
	        checkOutDate.toString(), 
	        contact, 
	        agentContact, 
	        price, 
	        state
	    );

	    message.setText(emailContent);
	    message.setFrom(fromEmailId);
	    mailSender.send(message);
	}


}
