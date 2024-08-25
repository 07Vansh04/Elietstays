package com.validation.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;

@Entity
public class Property {
	
	@Id
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	public int propertyId;
        
	
	   @OneToMany(mappedBy = "property")
       private List<Booking> bookings= new ArrayList<>();
	   
	    private String description;
	    private double price;
	  
	   
		private String status;

	    private String address;
	    private String city;
	    private String state;
	 
	    private String country;

	    private int bedrooms;
	    private int bathrooms;
	    private int squareFeet;
	   
	   
	    private int parking;
	   
	    @Lob
	    @Column(name = "picture", columnDefinition = "LONGBLOB")
	    public String picture;
		  
	    private String agentName;
	    private String agentContact;
	    
	    private boolean pool;
	    private boolean fireplace;
	    private boolean garden;
	    private boolean balcony;
	    private boolean basement;
	    private boolean furnished;

	    private float latitude;
	    private float longitude;

	  
	    public List<Booking> getBookings() {
			return bookings;
		}
		public void setBookings(List<Booking> bookings) {
			this.bookings = bookings;
		}
	    public int getPropertyId() {
			return propertyId;
		}
		public void setPropertyId(int propertyId) {
			this.propertyId = propertyId;
		}
		public String getDescription() {
			return description;
		}
		public void setDescription(String description) {
			this.description = description;
		}
		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getState() {
			return state;
		}
		public void setState(String state) {
			this.state = state;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public int getBedrooms() {
			return bedrooms;
		}
		public void setBedrooms(int bedrooms) {
			this.bedrooms = bedrooms;
		}
		public int getBathrooms() {
			return bathrooms;
		}
		public void setBathrooms(int bathrooms) {
			this.bathrooms = bathrooms;
		}
		public int getSquareFeet() {
			return squareFeet;
		}
		public void setSquareFeet(int squareFeet) {
			this.squareFeet = squareFeet;
		}
		public int getParking() {
			return parking;
		}
		public void setParking(int parking) {
			this.parking = parking;
		}
		public boolean isPool() {
			return pool;
		}
		public void setPool(boolean pool) {
			this.pool = pool;
		}
		public boolean isFireplace() {
			return fireplace;
		}
		public void setFireplace(boolean fireplace) {
			this.fireplace = fireplace;
		}
		public boolean isGarden() {
			return garden;
		}
		public void setGarden(boolean garden) {
			this.garden = garden;
		}
		public boolean isBalcony() {
			return balcony;
		}
		public void setBalcony(boolean balcony) {
			this.balcony = balcony;
		}
		public boolean isBasement() {
			return basement;
		}
		public void setBasement(boolean basement) {
			this.basement = basement;
		}
		public boolean isFurnished() {
			return furnished;
		}
		public void setFurnished(boolean furnished) {
			this.furnished = furnished;
		}
		public float getLatitude() {
			return latitude;
		}
		public void setLatitude(float latitude) {
			this.latitude = latitude;
		}
		public float getLongitude() {
			return longitude;
		}
		public void setLongitude(float longitude) {
			this.longitude = longitude;
		}
		public String getPicture() {
			return picture;
		}
		public void setPicture(String picture) {
			this.picture = picture;
		}
		public String getAgentName() {
			return agentName;
		}
		public void setAgentName(String agentName) {
			this.agentName = agentName;
		}
		public String getAgentContact() {
			return agentContact;
		}
		public void setAgentContact(String agentContact) {
			this.agentContact = agentContact;
		}
		public Property() {
			super();
			// TODO Auto-generated constructor stub
		}
		
}
	    
