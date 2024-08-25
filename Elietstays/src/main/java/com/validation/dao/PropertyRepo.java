package com.validation.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.validation.entities.Property;
import com.validation.entities.Registration;

public interface PropertyRepo extends 	JpaRepository<Property, Integer>{

	
	
}
