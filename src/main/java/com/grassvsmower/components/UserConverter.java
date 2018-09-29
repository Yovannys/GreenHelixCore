package com.grassvsmower.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.grassvsmower.entities.User;
import com.grassvsmower.model.UserModel;

@Component("userConverter")
public class UserConverter {
	
	 @Autowired
	 @Qualifier("profileConverter")
	 private ProfileConverter profileConverter;
	
	  public User userModel2User(UserModel userModel){
		  User users = null;  
		  
		  if (userModel!=null) {
	        users = new User();
	        users.setId(userModel.getId());
	        users.setUsername(userModel.getUsername());
	        users.setFirstname(userModel.getFirstname());
	        users.setLastname(userModel.getLastname());
	        users.setToken(userModel.getToken());
	        users.setEmail(userModel.getEmail());
	        users.setEnabled(userModel.isEnabled());
	        users.setTipoUsuario(userModel.getTipoUsuario());
	        users.setPicture(userModel.getPicture());
		  }	        
	        return users;        
	    }
	    
	    
	    public UserModel user2UserModel(User users){
	    	
	    	UserModel userModel = null;
	    	
	    	if (users!=null) {
	        
	        userModel = new UserModel();
	        userModel.setId(users.getId());
	        userModel.setFirstname(users.getFirstname());
	        userModel.setLastname(users.getLastname());
	        userModel.setUsername(users.getUsername());
	        userModel.setToken(users.getToken());
	        userModel.setEmail(users.getEmail());
	        userModel.setEnabled(users.isEnabled());
	        userModel.setTipoUsuario(userModel.getTipoUsuario());
	        userModel.setPicture(users.getPicture());
	        
	        userModel.setProfile(users.getProfile());
	    	}
	        return userModel;        
	    }

}
