package com.grassvsmower.services;

import java.util.List;

import com.grassvsmower.entities.Works;
import com.grassvsmower.model.ProfileModel;
import com.grassvsmower.model.UserModel;
import com.grassvsmower.model.WorksModel;
import com.grassvsmower.wrappers.WorkWrapperHome;

public interface ProfileService {
	
	
	 public ProfileModel setWorksToProfileByUser(UserModel userModel, List<WorksModel> worksModel);
	 
	 public ProfileModel update(ProfileModel profileModel);
	 
	 public WorkWrapperHome [] getworks (String username);
	 
	 public boolean checkShowHome(long index);

	}
