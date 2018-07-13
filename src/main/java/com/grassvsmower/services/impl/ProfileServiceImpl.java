/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grassvsmower.services.impl;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.grassvsmower.components.ProfileConverter;
import com.grassvsmower.components.UserConverter;
import com.grassvsmower.components.WorksConverter;
import com.grassvsmower.entities.Profile;
import com.grassvsmower.entities.User;
import com.grassvsmower.entities.Works;
import com.grassvsmower.model.ProfileModel;
import com.grassvsmower.model.UserModel;
import com.grassvsmower.model.WorksModel;
import com.grassvsmower.repository.ProfileRepository;
import com.grassvsmower.repository.UserRepository;
import com.grassvsmower.repository.WorkRepository;
import com.grassvsmower.services.ProfileService;
import com.grassvsmower.services.UserService;
import com.grassvsmower.utils.Utils;
import com.grassvsmower.wrappers.WorkWrapperHome;

/**
 *
 * @author Adriana
 */

@Service("profileServiceImpl")
public class ProfileServiceImpl implements ProfileService{
	
	private Logger LOG = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    @Qualifier("profileRepository")
    private ProfileRepository profileRepository;
    
    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;
    
    @Autowired
    @Qualifier("workRepository")
    private WorkRepository workRepository;
       
    @Autowired
    @Qualifier("profileConverter")
    private ProfileConverter profileConverter;
    
    @Autowired
    @Qualifier("worksConverter")
    private WorksConverter worksConverter;
    
    @Autowired
    @Qualifier("userConverter")
    private UserConverter userConverter;
    
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;
    
    @Autowired
    @Qualifier("utils")
    private Utils utils;
    
    
   	@Override
    public ProfileModel setWorksToProfileByUser(UserModel userModel, List<WorksModel> worksModel){
   	 User user = userRepository.findByToken(userModel.getToken());
   	 if (user!=null){
   		
	       Profile profile = user.getProfile();
	        	 
		 // Perfil - works
		 for(WorksModel itemsWork : worksModel){
			 Works works = worksConverter.workModel2Works(itemsWork);
			 profile.addWork(works);
			 profileRepository.save(profile);
			 
			 works.setProfile(profile);
			 workRepository.save(works);
		 }    	   
   	    
   	 }else{
   		 return null;
   	 }
    
   	  User retval = userRepository.findByToken(userModel.getToken());    
      return profileConverter.profile2profileModel(retval.getProfile()) ;
      
    }


	@Override
	public ProfileModel update(ProfileModel profileModel) {
		
		if (profileModel!=null) {
			User userSave = userRepository.findById(profileModel.getId());
			Profile pf = userSave.getProfile();
			
			//Updating
			pf.setDescription(profileModel.getDescription());
			pf.setAnotherServices(profileModel.getAnotherServices());
			pf.setTitle(profileModel.getTitle());
			pf.setLanguage(profileModel.getLanguage());
			pf.setUrl(profileModel.getUrl());
			
			
			userSave.setProfile(pf);
			userSave.setTipoUsuario(2);
			User tempObj = userRepository.save(userSave);
			UserModel retval = userConverter.user2UserModel(tempObj);
			return profileConverter.profile2profileModel(retval.getProfile()) ;
		}		
		
		
		return null;
		
	}


	@Override
	public WorkWrapperHome[] getworks(String username) {
		
		List<WorkWrapperHome> retval = new ArrayList<WorkWrapperHome>();
		UserModel usr = userService.findByUsername(username);
		List<Works> workList = usr.getProfile().getWorks();
		
		if (workList!=null && !workList.isEmpty()) {
			for (Works item : workList) {
				
				WorkWrapperHome workWrapperHome = new WorkWrapperHome();
				workWrapperHome.setAfterPhoto(Base64.getEncoder().withoutPadding().encodeToString( utils.blobToByte(item.getAfterPhoto())));
			//	workWrapperHome.setBeforePhoto(Base64.getEncoder().withoutPadding().encodeToString( utils.blobToByte(item.getBeforePhoto())));
				workWrapperHome.setDate(item.getDate());
				workWrapperHome.setId(item.getId());
				workWrapperHome.setShowHomePage(item.isShowHomePage());
				retval.add(workWrapperHome);
			}
		}else {
			return null;
		}
		
		//Order desc
		Collections.reverse(retval);
				
		WorkWrapperHome[] workWrapperHomeArr = retval.toArray(new WorkWrapperHome[retval.size()]);	
			
		return workWrapperHomeArr;
	}


	@Override
	public boolean checkShowHome(long index) {
		//Search
		Works work = workRepository.findById(index);
		return false;
	}

	 
}
