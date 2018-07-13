package com.grassvsmower.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "log")
public class Log {

	 @Id
	 @GeneratedValue
	 @Column(name = "id")
	 private int id;
	 
	 @Column(name = "date")
	 private Date date;
	 
	 @Column(name = "details")
	 private String details;
	 
	 @Column(name = "username")
	 private String username;
	 
	 @Column(name = "url")
	 private String url;
	 
	 @Column(nullable = false, updatable = false)
	 @CreationTimestamp
	 private Date createdDate;
	 	
	 @Column(nullable = false)
	 @UpdateTimestamp
	 private Date lastUpdatedDate;
	 
	public Log() { }
	 
	 
	public Log(Date date, String details, String username, String url) {
		super();
		
		this.date = date;
		this.details = details;
		this.username = username;
		this.url = url;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	 
	 
}
