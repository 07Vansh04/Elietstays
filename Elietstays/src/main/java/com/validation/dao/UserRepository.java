package com.validation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.validation.entities.Registration;

public interface UserRepository  extends 	JpaRepository<Registration, String>{

	
	public Registration findByEmail(String email);
}
