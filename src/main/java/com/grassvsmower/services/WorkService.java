package com.grassvsmower.services;

import java.util.List;

import com.grassvsmower.entities.Profile;
import com.grassvsmower.entities.Works;
import com.grassvsmower.wrappers.WorkWrapperHome;

public interface WorkService {
	
	public List<Works> findAllByProfile(Profile profile);
	
	public Works findById(Long id);
	
	public boolean delete(Long id);
	
	public Works canSetHome(Long id);
	
	public Works setHome(Long id);
	
	public boolean isFullCupsToSetHome (long id);
	
	public List<WorkWrapperHome> findAll(int idprofile);
	
}
