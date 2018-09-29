package com.grassvsmower.entities;

import java.sql.Blob;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

@Entity
public class Works {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	private Profile profile;
	
    private Blob beforePhoto;
	
	private Blob afterPhoto;
	
	private boolean showHomePage;
	
	private Date date;
	 
//	@PrePersist
//    void preInsert() {
//		showHomePage = false;
//    }
		
	public Works() {
		
	}	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Blob getBeforePhoto() {
		return beforePhoto;
	}

	public void setBeforePhoto(Blob beforePhoto) {
		this.beforePhoto = beforePhoto;
	}

	public Blob getAfterPhoto() {
		return afterPhoto;
	}

	public void setAfterPhoto(Blob afterPhoto) {
		this.afterPhoto = afterPhoto;
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public boolean isShowHomePage() {
		return showHomePage;
	}

	public void setShowHomePage(boolean showHomePage) {
		this.showHomePage = showHomePage;
	}


	public Date getDate() {
		return date;
	}


	public void setDate(Date date) {
		this.date = date;
	}	
	
	
	
}
