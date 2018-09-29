package com.grassvsmower.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Profile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Size(min=2, max=35, message="Title should be have between 2 and 35 characters")
	@NotEmpty(message = "Title must not be empty")
	@Pattern(message = "Invalid Title", regexp = "[a-zA-Z0-9-_ ]+")
	@NotNull(message = "Title Name is a required field")
	private String title;
	
	@Pattern(message = "Invalid URL", regexp = "^(http:\\/\\/|https:\\/\\/)?(www.)?([a-zA-Z0-9]+).[a-zA-Z0-9]*.[a-z]{3}.?([a-z]+)?$")
	private String url;
	
	private String language;
	
 	@NotNull(message = "Description is a required field")
	private String description;
	
	private String anotherServices;
	
	private String score;
	
	private String unscore;
	
	private String postCount;
	
	private String uid;
	
	private String signed;
	
	private String phone;
	
	private boolean accessfirstTime = false;
	
	@NotNull(message = "Address is a required field")
	@Pattern(message = "Invalid Title", regexp = "[a-zA-Z0-9,.-_ ]+")
	private String address;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	@JsonIgnore
	private User user;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="profile")  
	@JsonIgnore
	private List<Works> works = new ArrayList<Works>();
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy="profile")  
	@JsonIgnore
	private Set<Comment> comment = new HashSet<Comment>();
	
	
	@PrePersist
    void preInsert() {
		if (language==null)
		language = "en_US";
		
		if (score==null)
		score = Integer.toString(0);
		
		if (unscore==null)
		unscore = Integer.toString(0);
    }
	
	public Profile() {
		
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Works> getWorks() {
		return works;
	}

	
	public void setWorks(List<Works> works) {
		this.works = works;
	}
	
	
	public void addWork(Works work) {
		this.works.add(work);
	}
	
	public void removeWork(Works work) {
		this.works.remove(work);
	}

	public String getAnotherServices() {
		return anotherServices;
	}

	public void setAnotherServices(String anotherServices) {
		this.anotherServices = anotherServices;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public boolean isAccessfirstTime() {
		return accessfirstTime;
	}

	public void setAccessfirstTime(boolean accessfirstTime) {
		this.accessfirstTime = accessfirstTime;
	}

	public Set<Comment> getComment() {
		return comment;
	}

	public void setComment(Set<Comment> comment) {
		this.comment = comment;
	}

	public String getPostCount() {
		return postCount;
	}

	public void setPostCount(String postCount) {
		this.postCount = postCount;
	}	
	
}
