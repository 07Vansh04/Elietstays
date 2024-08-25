package com.validation.controller;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.validation.dao.PropertyRepo;
import com.validation.dao.UserRepository;
import com.validation.entities.Property;
import com.validation.entities.Registration;
import com.validation.services.BookingService;
import com.validation.services.PropertyService;
import com.validation.services.UserService;

import jakarta.validation.Valid;

@Controller
public class ValidationController {

	@Autowired
	private UserService userService;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PropertyRepo propertyrepo;

	@Autowired
	PropertyService propertyservice;

	@Autowired
	BookingService bookingService;

	@GetMapping("/home")
	public String showHomePage() {
		return "Home"; // Ensure this matches the template name (home.html)
	}

	@GetMapping("/login")
	public String openForm() {

		System.out.println("GET: email here");
		return "Login";
	}

	@PostMapping(value = "/login")
	public String loginForm(@RequestParam String email, @RequestParam String password, Model model,
			final RedirectAttributes redirectAttributes) {
		System.out.println("POST: Login attempt with email: " + email);
		if (userService.checkUser(email, password)) {
			if ("admin@gmail.com".equals(email)) {
				System.out.println("ADMIN_POST " + email);
				return "Admin";
			}
			Registration registration = userRepository.findByEmail(email);
			System.out.println("Successful login for: " + email);

			redirectAttributes.addFlashAttribute("name", registration.getUserName());
			return "redirect:explore"; // Redirect to avoid form resubmission
		}
		model.addAttribute("error", "Invalid credentials");
		System.out.println("Invalid credentials");
		return "Login"; // Return to the login page on failure
	}

	@PostMapping("/register")
	public String processForm(@Valid @ModelAttribute("user") Registration user, BindingResult result, Model model) {
		if (result.hasErrors()) {

			System.out.println("error" + result.toString());
			model.addAttribute("user", user);
			return "Registration";
		}

		System.out.println(user);

		userService.saveUser(user);

		return "Login";
	}

	@GetMapping("/register")
	public String registerPage(Model model) {
		model.addAttribute("user", new Registration());
		return "Registration";
	}

	@GetMapping("/explore")
	public String ExploreHotels(@RequestParam(required = false) String name, Model model) {
		List<Property> properties = propertyservice.getAllProperties();
		model.addAttribute("properties", properties);
		if (name != null) {
			model.addAttribute("name", name);
		}
		return "Explore";
	}

///////////////////////////////////////////////////////////////////////////////

	@PostMapping("/postproperty")
	public String getData(@Valid @ModelAttribute("property") Property property, @RequestParam("pic") MultipartFile file)
			throws IOException {

		System.out.println("Property: " + property);
		System.out.println("File: " + file.getOriginalFilename());
		if (!file.isEmpty()) {

			property.setPicture(Base64.getEncoder().encodeToString(file.getBytes()));
		}
		propertyservice.saveProperty(property);

		return "redirect:/crudproperty";
	}

	@GetMapping("/crudproperty")
	public String crudProperty(Model model) {
		List<Property> properties = propertyservice.getAllProperties();
		model.addAttribute("properties", properties);

		return "CrudProperty";
	}

	@GetMapping("/admin")
	public String AdminPanel() {
		return "Admin";
	}

	@GetMapping("/edit/{id}")
	public String editProperty(@PathVariable("id") int id, Model model) throws IOException {

		Property property = propertyservice.getPropertyById(id);
		model.addAttribute("property", property);
		return "EditProperty";
	}

	@PostMapping("/updateProperty")
	public String updateProperty(@ModelAttribute("property") Property property, @RequestParam("pic") MultipartFile file)
			throws IOException {
		if (!file.isEmpty()) {
			property.setPicture(Base64.getEncoder().encodeToString(file.getBytes()));
		}
		propertyservice.updateProperty(property.getPropertyId(), property);
		return "redirect:/crudproperty";
	}

	@GetMapping("/delete/{id}")
	public String deleteProperty(@PathVariable("id") int id) {
		propertyservice.deleteProperty(id);
		return "redirect:/crudproperty";
	}
	/////////////////////////////////////////////

	@PostMapping("/searchProperties")
	public String searchProperties(
	        @RequestParam("checkinDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkInDate,
	        @RequestParam("checkoutDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkOutDate,
	        Model model) {
	    System.out.println("checkInDate: " + checkInDate);
	    System.out.println("checkOutDate: " + checkOutDate);

	    List<Property> property = bookingService.findUnbookedProperties(checkInDate, checkOutDate);
	    for (Property property2 : property) {
			System.out.println(property2.propertyId);
		}

	    model.addAttribute("properties", property);
	    return "AvailableProperty";
	}


	/////////////////////
	@GetMapping("/booking/{propertyId}")
	public String showBookingForm(@PathVariable("propertyId") int propertyId, Model model) {

		Property property = propertyrepo.findById(propertyId).orElse(null);
		model.addAttribute("property", property);
		return "Booking";
	}

	/////

	@PostMapping("/processBooking")
	public String Booking(@RequestParam("email") String email, @RequestParam("contact") String contact,

			@RequestParam("checkInDate") String checkInDate, @RequestParam("checkOutDate") String checkOutDate,

			@RequestParam("billingName") String billingName, @RequestParam("cardNumber") String cardNumber,

			@RequestParam("expiryDate") String expiryDate, @RequestParam("cvv") String cvv, @RequestParam("totalPrice") double price,
			@RequestParam int propertyId, Model model) {

		boolean paymentSuccess = bookingService.processpayment(cardNumber, expiryDate, cvv);
		if (paymentSuccess) {
			
			
			
			  bookingService.saveBooking(email, contact, checkInDate, checkOutDate,
			  billingName, propertyId);
			 
			  Property property = propertyrepo.findById(propertyId).orElse(null);
			  
			   
			bookingService.sendConfirmationEmail(email,contact, checkInDate, checkOutDate,contact,price,property.getState(),
					  billingName);
			return "redirect:/explore";

		}

		return "redirect:/explore";

	}

}
