package com.grassvsmower.components;

import org.springframework.stereotype.Component;

import com.grassvsmower.entities.Contact;
import com.grassvsmower.entities.Profile;
import com.grassvsmower.model.ContactModel;
import com.grassvsmower.model.ProfileModel;

@Component("profileConverter")
public class ProfileConverter {
	
	public Profile profileModel2Profile(ProfileModel profileModel){
		Profile retval = null;
		
		if (profileModel!=null) {
		
			retval = new Profile();
			retval.setId(profileModel.getId());
			retval.setLanguage(profileModel.getLanguage());
			retval.setTitle(profileModel.getTitle());
			retval.setUrl(profileModel.getUrl());
			retval.setAnotherServices(profileModel.getAnotherServices());
			retval.setDescription(profileModel.getDescription());
			retval.setScore(profileModel.getScore());
			retval.setUid(profileModel.getUid());
			retval.setUnscore(profileModel.getUnscore());
			retval.setAddress(profileModel.getAddress());
			retval.setSigned(profileModel.getSigned());
			retval.setPhone(profileModel.getPhone());
			retval.setPostCount(profileModel.getPostCount());
		}
		return retval;
	}
	
    public ProfileModel profile2profileModel(Profile profile){
    	
    	ProfileModel retval = null;
    	
    	if (profile!=null) {
		
    		retval = new ProfileModel();
    		retval.setId(profile.getId());
	    	retval.setLanguage(profile.getLanguage());
	    	retval.setTitle(profile.getTitle());
	    	retval.setUrl(profile.getUrl());
	    	retval.setAnotherServices(profile.getAnotherServices());
			retval.setDescription(profile.getDescription()); 
			retval.setScore(profile.getScore());
			retval.setScore(profile.getUid());
			retval.setUnscore(profile.getUnscore());
			retval.setUnscore(profile.getAddress());
			retval.setSigned(profile.getSigned());
			retval.setPhone(profile.getPhone());
			retval.setPostCount(profile.getPostCount());
		
    	}
		return retval;
	}

}
