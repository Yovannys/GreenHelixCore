package com.grassvsmower.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileModel {
	
	@Id
	@GeneratedValue
	@Column(name="id")
	private Long id;
	
	private String title;
	
	private String url;
	
	private String language;
	
	private String description;
	
	private String anotherServices;
	
	private String score;
	
	private String uid;
	
	private String unscore;
	               
	
	public ProfileModel() {
		
	}		

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAnotherServices() {
		return anotherServices;
	}

	public void setAnotherServices(String anotherServices) {
		this.anotherServices = anotherServices;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUnscore() {
		return unscore;
	}

	public void setUnscore(String unscore) {
		this.unscore = unscore;
	}	

}
