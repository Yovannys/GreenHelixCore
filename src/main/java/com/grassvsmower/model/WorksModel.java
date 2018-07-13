package com.grassvsmower.model;

import java.sql.Blob;
import java.sql.Date;

import com.grassvsmower.entities.Profile;

public class WorksModel {
	
	private Long id;
	
	private Profile profile;
	
    private Blob beforePhoto;
	
	private Blob afterPhoto;
	
	private boolean showHomePage;
	
	private Date date;
	
	
	public WorksModel() {
		
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
