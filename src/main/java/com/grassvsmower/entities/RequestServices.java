package com.grassvsmower.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class RequestServices {
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
//	@Size(min=2, max=35, message="FirstName should be have between 2 and 35 characters")
//	@NotEmpty(message = "Name must not be empty")
//	@Pattern(message = "Invalid FirstName", regexp = "[a-zA-Z0-9-_]+")
//	@NotNull(message = "First Name is a required field")
	private String name;
	
//	@Size(min=2, max=35, message="LastName should be have between 2 and 35 characters")
//	@NotEmpty(message = "Name must not be empty")
//	@Pattern(message = "Invalid LastName", regexp = "[a-zA-Z0-9-_]+")
//	@NotNull(message = "Last Name is a required field")
	private String lastName;
	
	private String phone;
	
	@Email(message="Email should be a valid email")
	private String email;
	
//	@NotEmpty(message = "Services must not be empty")
//	@NotNull(message = "Services is a required field")
//	@Pattern(message = "Invalid services", regexp = "[a-zA-Z0-9,.-_ ]+")
	private String services;
	
	
	private String address;
	
	private String date;
	
	
	@NotNull(message = "Profile Id is a required field")
	private int profileid; 
	
	public RequestServices() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getProfileid() {
		return profileid;
	}

	public void setProfileid(int profileid) {
		this.profileid = profileid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}	

}
