package com.grassvsmower.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
public class Contact {
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	@Size(min=2, max=35, message="FirstName should be have between 2 and 35 characters")
	@NotEmpty(message = "Name must not be empty")
	@Pattern(message = "Invalid FirstName", regexp = "[a-zA-Z0-9-_]+")
	@NotNull(message = "First Name is a required field")
	private String name;
	
	@Size(min=2, max=35, message="LastName should be have between 2 and 35 characters")
	@NotEmpty(message = "Name must not be empty")
	@Pattern(message = "Invalid LastName", regexp = "[a-zA-Z0-9-_]+")
	@NotNull(message = "Last Name is a required field")
	private String lastName;
	
	private String phone;
	
	@Email(message="Email should be a valid email")
	private String email;
	
	@NotEmpty(message = "Subject must not be empty")
	@NotNull(message = "Subject name is a required field")
	private String subject;
	
	@NotEmpty(message = "Message must not be empty")
	@NotNull(message = "Message name is a required field")
	private String message;
	
	public Contact() {
		
	}
		
	

	public Contact(String name, String lastName, String phone, String email, String subject, String message) {
		super();
		this.name = name;
		this.lastName = lastName;
		this.phone = phone;
		this.email = email;
		this.subject = subject;
		this.message = message;
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



	public String getSubject() {
		return subject;
	}



	public void setSubject(String subject) {
		this.subject = subject;
	}



	public String getMessage() {
		return message;
	}



	public void setMessage(String message) {
		this.message = message;
	}

		

}
