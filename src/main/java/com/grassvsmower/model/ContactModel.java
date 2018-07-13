package com.grassvsmower.model;

public class ContactModel {
	
	
	private Long id;
	
	private String firstname;
	
	private String lastname;
	
	private String phone;
	
	private String email;
	
	private String subject;
	
	private String message;
	
	
	public ContactModel() {
		
	}	
	
	public ContactModel(String name, String lastName, String phone, String email, String subject, String message) {
		super();
		this.firstname = name;
		this.lastname = lastName;
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

	

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
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

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

		
	
}
