package com.grassvsmower.wrappers;

import java.util.Date;
import java.util.List;

public class ProfileWrapperHome {
	
    private Long id;
	
	private String title;
	
	private String url;
	
	private String language;
	
	private String description;
	
	private String anotherServices;
	
	private WorkWrapperHome works;
	
	private String score;
	
	private String uid;
	
	private String unscore;
	
	private String address;
	
	private String signed;
	
	private String phone;
	
	private String postCount;
	
	private boolean setAccessfirstTime;
	
	//Temp
	private String rssLink;
	
	public ProfileWrapperHome() {
		
	}

	public ProfileWrapperHome(Long id, String title, String url, String language, String description,
			String anotherServices, WorkWrapperHome works) {
		super();
		this.id = id;
		this.title = title;
		this.url = url;
		this.language = language;
		this.description = description;
		this.anotherServices = anotherServices;
		this.works = works;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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

	public WorkWrapperHome getWorks() {
		return works;
	}

	public void setWorks(WorkWrapperHome works) {
		this.works = works;
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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getSigned() {
		return signed;
	}

	public void setSigned(String signed) {
		this.signed = signed;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRssLink() {
		return rssLink;
	}

	public void setRssLink(String rssLink) {
		this.rssLink = rssLink;
	}

	public boolean isSetAccessfirstTime() {
		return setAccessfirstTime;
	}

	public void setSetAccessfirstTime(boolean setAccessfirstTime) {
		this.setAccessfirstTime = setAccessfirstTime;
	}

	public String getPostCount() {
		return postCount;
	}

	public void setPostCount(String postCount) {
		this.postCount = postCount;
	}	
	
}
