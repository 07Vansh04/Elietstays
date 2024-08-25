package com.validation.dao;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.validation.entities.Booking;
import com.validation.entities.Property;


public interface BookingRepo extends JpaRepository<Property, Integer>{
	
	  @Query("SELECT p.propertyId FROM Property p WHERE p.propertyId NOT IN (" +
	           "SELECT b.property.propertyId FROM Booking b WHERE " +
	           "(b.checkInDate < :checkOutDate AND b.checkOutDate > :checkInDate))")
	    List<Integer> findUnbookedPropertyIds(@Param("checkInDate") LocalDate checkInDate,
	                                          @Param("checkOutDate") LocalDate checkOutDate);

	void save(Booking booking);

}
