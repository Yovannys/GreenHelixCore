package com.grassvsmower.wrappers;

import java.util.List;

import com.grassvsmower.model.RssData;

public class HomeWrapper {
	
	private long total_elements;
	private int total_pages;
	private List<UserWrapperHome>data;
	
	
		
	public HomeWrapper() {
		super();
	}
	public long getTotal_elements() {
		return total_elements;
	}
	public void setTotal_elements(long total_elements) {
		this.total_elements = total_elements;
	}
	public int getTotal_pages() {
		return total_pages;
	}
	public void setTotal_pages(int total_pages) {
		this.total_pages = total_pages;
	}
	public List<UserWrapperHome> getData() {
		return data;
	}
	public void setData(List<UserWrapperHome> data) {
		this.data = data;
	}
	
}
