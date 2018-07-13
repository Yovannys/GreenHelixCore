package com.grassvsmower.wrappers;

import java.sql.Date;

public class WorkWrapperHome {
	
	private Long id;
	
	private String beforePhoto;
	
	private String afterPhoto;
	
	private boolean showHomePage;
	
	private Date date;
	
	
	public WorkWrapperHome() {
		
	}
	

	public WorkWrapperHome(Long id, String beforePhoto, String afterPhoto, boolean showHomePage, Date date) {
		super();
		this.id = id;
		this.beforePhoto = beforePhoto;
		this.afterPhoto = afterPhoto;
		this.showHomePage = showHomePage;
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBeforePhoto() {
		return beforePhoto;
	}

	public void setBeforePhoto(String beforePhoto) {
		this.beforePhoto = beforePhoto;
	}

	public String getAfterPhoto() {
		return afterPhoto;
	}

	public void setAfterPhoto(String afterPhoto) {
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
