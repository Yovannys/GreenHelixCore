/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grassvsmower.services.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.hibernate.loader.custom.Return;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
import com.grassvsmower.services.UserService;
import com.grassvsmower.utils.Utils;
import com.grassvsmower.wrappers.HomeWrapper;
import com.grassvsmower.wrappers.ProfileWrapperHome;
import com.grassvsmower.wrappers.UserWrapperHome;
import com.grassvsmower.wrappers.WorkWrapperHome;

/**
 *
 * @author Adriana
 */

@Service("userServiceImpl")
public class UserServiceImpl implements UserService{
	
	private Logger LOG = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    @Qualifier("userRepository")
    private UserRepository userRepository;
    
    @Autowired
    @Qualifier("profileRepository")
    private ProfileRepository profileRepository;
    
    @Autowired
    @Qualifier("profileServiceImpl")
    private ProfileServiceImpl profileServiceImpl;
    
      
    @Autowired
    @Qualifier("workRepository")
    private WorkRepository workRepository;
    
    @Autowired
    @Qualifier("userConverter")
    private UserConverter userConverter;
    
    @Autowired
    @Qualifier("profileConverter")
    private ProfileConverter profileConverter;
    
    @Autowired
    @Qualifier("worksConverter")
    private WorksConverter worksConverter;
    
    @Autowired
    @Qualifier("utils")
    private Utils utils;
    
    private UserModel registerUser(UserModel usermodel) {
		// TODO Auto-generated method stub
    	
    	User retval = null;
    	
    	User user = new User();
    	if (usermodel.getUsername()!=null && !usermodel.getUsername().equals("")) user.setUsername(usermodel.getUsername());
    	if (usermodel.getEmail()!=null && !usermodel.getEmail().equals("")) user.setEmail(usermodel.getEmail());
    	if (usermodel.getFirstname()!=null && !usermodel.getFirstname().equals("")) user.setFirstname(usermodel.getFirstname());
    	if (usermodel.getLastname()!=null && !usermodel.getLastname().equals("")) user.setLastname(usermodel.getLastname());
    	
    	if (usermodel.getTipoUsuario()==2)
    	user.setTipoUsuario(new Integer(usermodel.getTipoUsuario()));
    	else
    	user.setTipoUsuario(2);
    	
    	user.setToken(usermodel.getToken());
    	    	
    	userRepository.save(user);
    	
    	Profile profile = new Profile();
    	profile.setUser(user);
    	
    	Works works = new Works();
		profile.addWork(works);
    	
		workRepository.save(works);
    	profileRepository.save(profile);
    	    	
    	user.setProfile(profile);
    	retval = userRepository.save(user);
    	
    	if (retval!=null)
    	 return userConverter.user2UserModel(retval);
    	    	
    	return userConverter.user2UserModel(retval); 	
		
	}

	@Override
	public UserModel register(UserModel usermodel, ProfileModel profileModel, List<WorksModel> worksModel) {
		 User retval = null;
	try {
		
		 // setting values
		 Profile profile = profileConverter.profileModel2Profile(profileModel);
		 User user = userConverter.userModel2User(usermodel);
		 		 				 
		 // Perfil - works
		 
		 if (worksModel!=null && worksModel.isEmpty()) {
		 
			 for(WorksModel itemsWork:worksModel){
				 
				 Works works = worksConverter.workModel2Works(itemsWork);
				 profile.addWork(works);
				 profile.setUrl(utils.md5(user.getUsername().concat(user.getFirstname()).concat(user.getEmail()).concat(user.getLastname()))  );
				
				 profileRepository.save(profile);
				 
				 works.setProfile(profile);
				 workRepository.save(works);
			 }
			   		  
			// User
			 user.setProfile(profile);
			 retval = userRepository.save(user);
		 }
		
	} catch (Exception e) {
		LOG.error("METHOD : register() ->{}",e.getMessage());
	}
	return userConverter.user2UserModel(retval);
		  
	}
	
	@Override
	public List<UserWrapperHome> search(String crit) {
		 List<UserWrapperHome> retval = new ArrayList<UserWrapperHome>();
	       List<User> data = userRepository.findByCriteriaContaining(crit);
	       if (data!=null && !data.isEmpty()) {
		       for (User item : data) {
		          		    	   
		    	 //Setting UserWrapper
					UserWrapperHome userWrapperHome = new UserWrapperHome();
					userWrapperHome.setEmail(item.getEmail());
					userWrapperHome.setEnabled(item.isEnabled());
					userWrapperHome.setFirstname(item.getFirstname());
					userWrapperHome.setId(item.getId());
					userWrapperHome.setLastname(item.getLastname());
					userWrapperHome.setTipoUsuario(item.getTipoUsuario());
					userWrapperHome.setToken(item.getToken());
					userWrapperHome.setUsername(item.getUsername());
					
					//Setting ProfileWrapper
					ProfileWrapperHome profileWrapperHome = new ProfileWrapperHome(); 
					profileWrapperHome.setAnotherServices(item.getProfile().getAnotherServices());
					profileWrapperHome.setDescription(item.getProfile().getDescription());
					profileWrapperHome.setId(item.getProfile().getId());
					profileWrapperHome.setLanguage(item.getProfile().getLanguage());
					profileWrapperHome.setTitle(item.getProfile().getTitle());
					profileWrapperHome.setUrl(item.getProfile().getUrl());
					profileWrapperHome.setScore(item.getProfile().getScore());
					profileWrapperHome.setUnscore(item.getProfile().getUnscore());
										
					//Setting WorkeWrapper
					WorkWrapperHome worksWrapperHome = new WorkWrapperHome();
				
					
					if (item.getProfile().getWorks().get(0).getAfterPhoto()!=null)
						worksWrapperHome.setAfterPhoto(Base64.getEncoder().withoutPadding().encodeToString( utils.blobToByte(item.getProfile().getWorks().get(0).getAfterPhoto())));
						else
						worksWrapperHome.setAfterPhoto(null);
							
						if(item.getProfile().getWorks().get(0).getBeforePhoto()!=null)
						worksWrapperHome.setBeforePhoto(Base64.getEncoder().withoutPadding().encodeToString( utils.blobToByte(item.getProfile().getWorks().get(0).getBeforePhoto())));
						else
						worksWrapperHome.setBeforePhoto(null);
					
					
					worksWrapperHome.setId(item.getProfile().getWorks().get(0).getId());
					worksWrapperHome.setShowHomePage(item.getProfile().getWorks().get(0).isShowHomePage());
					worksWrapperHome.setDate(item.getProfile().getWorks().get(0).getDate());
					profileWrapperHome.setWorks(worksWrapperHome);
					
					userWrapperHome.setProfile(profileWrapperHome);
											
					retval.add(userWrapperHome);
		       }
	       }
	    return retval;
	}

	@Override
	public boolean checkToken() {
		UserModel usr = getUsernameByToken();
	    return (usr!=null);
	}

	@Override
	public UserModel getUsernameByToken() {
		
		UserModel retval = null;
		try {
			String token = getToken();
			if (token!=null && !token.equalsIgnoreCase("")) {
				retval =  userConverter.user2UserModel(userRepository.findByToken(token));
			}
		} catch (Exception e) {
			return retval;
		}
		return retval; 
	}

	@Override
	public Page<User> findAll(int page) {
		
		// Pageable pageable = PageRequest.of(0, 3, Sort.by("salary"));
	     
		   Page<User> pages = userRepository.findAll(new PageRequest(page, 4));
	         
	       if (pages!=null) {
	         return pages;
	       }
	       return null;
	}
	
	
	private boolean isContainAfterBefore(List<Works> listWorks ) {
		boolean retval  = false;
		
		if (listWorks!=null && !listWorks.isEmpty()) {
			for (Works item : listWorks) {
              
				if (item.getBeforePhoto()!=null) {
					retval  = true;
					break;
				}
				
			}
		}
		
		
		return retval;
	}
	
	@Override
	public HomeWrapper getHomeProfile (int page)  {
		
		HomeWrapper homeWrapper = new HomeWrapper();
		List<UserModel> listUser = new ArrayList<>();
		List<UserWrapperHome> retval = new ArrayList<>();
		
		Page<User> pages = findAll(page);
		
		System.out.println("Total Elements" + pages.getTotalElements());
		System.out.println("Total Pages" + pages.getTotalPages());
		
		if (pages.getContent()!=null && !pages.getContent().isEmpty()) {
			
			//Setting total page and elements
			homeWrapper.setTotal_pages(pages.getTotalPages());
			homeWrapper.setTotal_elements(pages.getTotalElements());
			
			List<User> userList = pages.getContent();
			
			for (User item : userList) {
				listUser.add(userConverter.user2UserModel(item));
			}
		}
				
		
		if (listUser!=null && !listUser.isEmpty()) {
		
			for (UserModel items : listUser) {
	
	            List<Works> listWorks = items.getProfile().getWorks();
	            
	            if (listWorks!=null && !listWorks.isEmpty()) {
	             	            	 
	            	if (isContainAfterBefore(listWorks)) {
	            		
	            		 //Remove all null values
				    	 
			    	      CollectionUtils.filter(listWorks, new Predicate() {
			    	        @Override
			    	              public boolean evaluate(Object arg0) {
			    	        	          Works comp = (Works) arg0;
			    	                      return (comp.getAfterPhoto()!=null && comp.getBeforePhoto()!=null && comp.isShowHomePage());
			    	              }
			    	        });
	            		
	            	}else {
	            		//Remove all photo where isShowHomePage is false
				    	 
			    	      CollectionUtils.filter(listWorks, new Predicate() {
			    	        @Override
			    	              public boolean evaluate(Object arg0) {
			    	        	          Works comp = (Works) arg0;
			    	                      return (comp.getAfterPhoto()!=null && comp.isShowHomePage());
			    	              }
			    	        });
	            	}           	
	            	 
			    	 items.getProfile().setWorks(listWorks);
	            }	  
				 
			}
	    }else {
	    	return null;
	    }
		
		//Setting all Wrapper

		   if  (listUser!=null && !listUser.isEmpty()) {
		
					for (UserModel item : listUser) {
						
						//Setting UserWrapper
						UserWrapperHome userWrapperHome = new UserWrapperHome();
						userWrapperHome.setEmail(item.getEmail());
						userWrapperHome.setEnabled(item.isEnabled());
						userWrapperHome.setFirstname(item.getFirstname());
						userWrapperHome.setId(item.getId());
						userWrapperHome.setLastname(item.getLastname());
						userWrapperHome.setTipoUsuario(item.getTipoUsuario());
						userWrapperHome.setToken(item.getToken());
						userWrapperHome.setUsername(item.getUsername());
						
						//Setting ProfileWrapper
						ProfileWrapperHome profileWrapperHome = new ProfileWrapperHome(); 
						profileWrapperHome.setAnotherServices(item.getProfile().getAnotherServices());
						profileWrapperHome.setDescription(item.getProfile().getDescription());
						profileWrapperHome.setId(item.getProfile().getId());
						profileWrapperHome.setLanguage(item.getProfile().getLanguage());
						profileWrapperHome.setTitle(item.getProfile().getTitle());
						profileWrapperHome.setUrl(item.getProfile().getUrl());
						profileWrapperHome.setScore(item.getProfile().getScore());
						profileWrapperHome.setUnscore(item.getProfile().getUnscore());
						
						//Setting WorkeWrapper
						WorkWrapperHome worksWrapperHome = new WorkWrapperHome();
						if (item.getProfile().getWorks().get(0).getAfterPhoto()!=null)
						worksWrapperHome.setAfterPhoto(Base64.getEncoder().withoutPadding().encodeToString( utils.blobToByte(item.getProfile().getWorks().get(0).getAfterPhoto())));
						else
						worksWrapperHome.setAfterPhoto(null);
							
						if(item.getProfile().getWorks().get(0).getBeforePhoto()!=null)
						worksWrapperHome.setBeforePhoto(Base64.getEncoder().withoutPadding().encodeToString( utils.blobToByte(item.getProfile().getWorks().get(0).getBeforePhoto())));
						else
						worksWrapperHome.setBeforePhoto(null);
							
						worksWrapperHome.setId(item.getProfile().getWorks().get(0).getId());
						worksWrapperHome.setShowHomePage(item.getProfile().getWorks().get(0).isShowHomePage());
						worksWrapperHome.setDate(item.getProfile().getWorks().get(0).getDate());
						
						profileWrapperHome.setWorks(worksWrapperHome);
						
						userWrapperHome.setProfile(profileWrapperHome);
												
						retval.add(userWrapperHome);
					}
					
		  }
		   
		homeWrapper.setData(retval);   
		return homeWrapper;
	}

	@Override
	public UserModel getProfile (UserModel usermodel) {
		UserModel retval = null;
		
		UserModel usr = findByUsername(usermodel.getUsername());
		 
		 if (usr!=null) {
			if (usr.getTipoUsuario()!=2) usr.setTipoUsuario(2);
			retval = usr;
		 }else {
			//Register User
			 return registerUser(usermodel);
		 }
				
		//User found
		if (retval!=null) {
			
			User userSave = null;
			
			UserModel usrToken = getUsernameByToken();
			
			//Token matched
			if(usrToken!=null) {
		      return retval;				
			}else {
				
				String token = getToken();
				if (token!=null && !token.equals("")) {
					usrToken = new UserModel();
					usrToken = userConverter.user2UserModel(userRepository.findByUsername(usermodel.getUsername()));
					userSave = userConverter.userModel2User(usrToken);
					userSave.setToken(token);
				}else {
					//Security Violation
					return null;
				}
				//Updatig token

				Profile pf = profileRepository.findByUserId(userSave.getId());
				userSave.setProfile(pf);
				userSave.setTipoUsuario(2);
				User tempObj = userRepository.save(userSave);
				retval = userConverter.user2UserModel(tempObj);
			}
			
		}
	
       return retval;
	}
	
	@Override
	public UserModel findByUsername(String username) {
		
		UserModel retval = null;
		
		try {
			
			retval = userConverter.user2UserModel(userRepository.findByUsername(username));
			
		} catch (Exception e) {
			retval = null;
		}
				
		return retval;
		
	}
	
	@Override
	public String getToken() {
		
		 String retval = "";
	        try {
	                  
	            Map <String, String> datos = utils.getHttpHeaders();
	            for (Map.Entry<String, String> entry : datos.entrySet()) {
	                String key = entry.getKey();
	                if (key.trim().equalsIgnoreCase("Api-Token")){
	                 retval = entry.getValue();
	                  break;
	                }

	            }
	        } catch (Exception e) {
	             return retval;
	        }
	         return retval;
		
	}

	@Override
	public boolean has_Token(String username) {
				
		boolean flag = true;
		
		if (username==null || username.equalsIgnoreCase("")) {
			flag = false;
			return flag;
		}
		
		UserModel usrToken = getUsernameByToken();
		
		//Token matched
		if(usrToken!=null) {
	      return flag;				
		}else {
			
			String token = getToken();
			if (token!=null && !token.equals("")) {
				UserModel usrTokenTmp = userConverter.user2UserModel(userRepository.findByUsername(username));
				User userSave = userConverter.userModel2User(usrTokenTmp);
				userSave.setToken(token);
				userRepository.save(userSave);
			}else {
				//Security Violation
				flag = false;
				return flag;
			}
		
		return flag;
	}
  }

	@Override
	public User findById(Long id) {
		return userRepository.findById(id);
	}

	private boolean isSameUID(Long id, String uid) {
		boolean retval = true;
		
		User user = findById(id);
		if (user!=null) {
			String clientUID = user.getProfile().getUid();
			 if (clientUID!=null) {
				 if ( clientUID.equalsIgnoreCase(uid.trim())) {
					 retval = true; 
				 }else {
					//Different Client
					Profile profile = user.getProfile();
					profile.setUid(String.valueOf(uid));
					user.setProfile(profile);
					userRepository.save(user);
					retval = false;
				 }
				 
			 }else {
				 //First time
				Profile profile = user.getProfile();
				profile.setUid(String.valueOf(uid));
				user.setProfile(profile);
				userRepository.save(user);
			    retval = false;
			 }
				 
		}else {
			retval = false;
		}
		
		return retval;
	}
	
	
	@Override
	public int setLike(Long id, String uid, boolean operation) {
		int retval = -1	;
		
		System.out.println("SET LIKE* *********************  ID  ********************" + id);
		System.out.println("SET LIKE* ********************** UID *******************" + uid);
		
		if (isSameUID(id, uid)) {
			User user = findById(id);
			try {
				if (operation) {
					retval = Integer.parseInt(user.getProfile().getScore());
				}else {
					retval = Integer.parseInt(user.getProfile().getUnscore());
				}
				
				return retval;
			 } catch (Exception e) {
				 return retval;
			}		
			
		}else {
			User user = findById(id);
			int value = 0;
			try {
				 if (operation) {
					 value = Integer.parseInt(user.getProfile().getScore());					
				 }else {
					 value = Integer.parseInt(user.getProfile().getUnscore());					
				 }
				 value+=1;			
				
			} catch (Exception e) {
				value+=1;
			}		
			
			retval = value;
			Profile profile = user.getProfile();
			
			if(operation) {
				profile.setScore(String.valueOf(value));
			}else {
				profile.setUnscore(String.valueOf(value));
			}			
			
			user.setProfile(profile);
			userRepository.save(user);
		}
		return retval;
	}

	
			    
}
