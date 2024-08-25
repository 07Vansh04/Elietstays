package com.validation.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.validation.dao.UserRepository;
import com.validation.entities.Registration;

@Service
public class UserService {
	@Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(Registration registration) {
        // Encrypt the password before saving
        registration.setPassword(passwordEncoder.encode(registration.getPassword()));
		 userRepository.save(registration); 
    System.out.println(registration.getPassword());
    
    }
    
    public boolean checkUser(String email,String rawPassword) {
    	
    	Registration registration = userRepository.findByEmail(email);
    	if ( registration!= null) {
    		return  passwordEncoder.matches(rawPassword, registration.getPassword()); 	
    	}    	
    	return false;
  	
    }
}
