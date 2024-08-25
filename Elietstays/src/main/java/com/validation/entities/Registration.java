package com.validation.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.persistence.Id;

@Entity
@Table(name = "user_registration")
public class Registration {
    
	  
  
  	 @Id
  	 @Email(regexp="^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}$")
  	 public String email;
  	 
  	 @NotBlank(message = " username can not be blank")
 	public String userName;
  	 
  	@NotEmpty(message = "Password is required")
  	 public String password;
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Registration() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Registration(String userName, String email, String password) {
		super();
		this.userName = userName;
		this.email = email;
		this.password = password;
	}
	@Override
	public String toString() {
		return "Registration [userName=" + userName + ", email=" + email + ", password=" + password + "]";
	}
  	 
	
}
