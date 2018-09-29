/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grassvsmower.services.impl;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialException;
import javax.transaction.Transactional;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.hibernate.engine.jdbc.BlobProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.grassvsmower.components.PostConverter;
import com.grassvsmower.components.ProfileConverter;
import com.grassvsmower.components.UserConverter;
import com.grassvsmower.components.WorksConverter;
import com.grassvsmower.entities.Comment;
import com.grassvsmower.entities.Profile;
import com.grassvsmower.entities.User;
import com.grassvsmower.entities.Works;
import com.grassvsmower.model.PostModel;
import com.grassvsmower.model.ProfileModel;
import com.grassvsmower.model.RssData;
import com.grassvsmower.model.UserModel;
import com.grassvsmower.model.WorksModel;
import com.grassvsmower.repository.PostRepository;
import com.grassvsmower.repository.ProfileRepository;
import com.grassvsmower.repository.UserRepository;
import com.grassvsmower.repository.WorkRepository;
import com.grassvsmower.services.UserService;
import com.grassvsmower.utils.Utils;
import com.grassvsmower.wrappers.HomeWrapper;
import com.grassvsmower.wrappers.ProfileWrapperHome;
import com.grassvsmower.wrappers.UserWrapperHome;
import com.grassvsmower.wrappers.WorkWrapperHome;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

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
    @Qualifier("postRepository")
    private PostRepository postRepository;
    
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
    @Qualifier("postConverter")
    private PostConverter postConverter;   
    
    
    @Autowired
    @Qualifier("profileConverter")
    private ProfileConverter profileConverter;
    
    @Autowired
    @Qualifier("worksConverter")
    private WorksConverter worksConverter;
    
    @Autowired
    @Qualifier("utils")
    private Utils utils;
    
     
    @Transactional
    private UserModel registerUser(UserModel usermodel) {
		// TODO Auto-generated method stub
    	
    	User retval = null;
    	
    	User user = new User();
    	
    	if (usermodel.getUsername()!=null && !usermodel.getUsername().equals("")) user.setUsername(usermodel.getUsername());
    	if (usermodel.getEmail()!=null && !usermodel.getEmail().equals("")) user.setEmail(usermodel.getEmail());
    	if (usermodel.getFirstname()!=null && !usermodel.getFirstname().equals("")) user.setFirstname(usermodel.getFirstname());
    	if (usermodel.getLastname()!=null && !usermodel.getLastname().equals("")) user.setLastname(usermodel.getLastname());
    	if (usermodel.getPicture()!=null && !usermodel.getPicture().equals("")) user.setPicture(usermodel.getPicture());
    	
    	if (usermodel.getTipoUsuario()==2)
    	user.setTipoUsuario(new Integer(usermodel.getTipoUsuario()));
    	else
    	user.setTipoUsuario(2);
    	
    	user.setToken(usermodel.getToken());
    	    	
    	userRepository.save(user);
    	
    	Profile profile = new Profile();
    	profile.setTitle("My Title");
    	profile.setDescription("My Description");
    	profile.setAddress("My Address");
    	profile.setAnotherServices("My Services");
    	profile.setPhone("555-555-5555");
    	
    	//
    	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    	Date today = Calendar.getInstance().getTime();        
    	String stringDate = df.format(today);
    	//
    	profile.setSigned(stringDate);
    	
    	profile.setAccessfirstTime(true);
    	
    	profile.setUser(user);
    	
    	Works works = new Works();
		
		try {
            byte[] decodedString = Base64.getDecoder().decode(new String("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iivrDoCiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKAPnP/gpdd7Pg9ocG7mTWVfHrtgmH/s3617R8GrT+z/hB4Vt9u3ydHtI8HtiFBXzr/wUdv28ReLPA/hm3bNxcNJKUHJJldIozj6q9fVVlaR6fZw28S7Y4EEaD0AGBXyeWfvM8xlRbRVOPzs2/uOenrVk/Qkooor6w6AooooA8s/bO8Ct48/Z412OKMyXOmquowgDJBiOX/8AIe8fjWV+wb4+Xxn+z/Y2jSbrrQJXsJQeu0HfGfpsYL/wE17NPAl1A8cirJHIpVlYZDA8EGvkX9nW9b9mL9rHXPBF+Wi0vXpBFZyMflY5LWzZ77lYocfxHHavkc0f1LOKGOfwVE6UvJ7xfzenoc9T3ain30PryiiivrjoCiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKKKACiiigAooooAKKK4v9oP4qx/Br4S6trjEfao4/Js1P8c7/Kn4A/MfZTWOJxEKFKVaq7Rim36IUpJK7PnmOYfHj/gocskI+0ab4Wk5bqEFqOv0+0Nx9RX11Xzl/wAE6/hhJofgPUvFl8rG88RTbIGf73kRk5bJ/vyFvrsU19G187wnQqfVJYysverSc35J7L7tV6mOHT5eZ9dQooor6g3CiiigAr53/b9+C03ibwta+NNJV11bwyP35j4drYNu3g9cxtlvozHsK+iKZcW8d3BJFKiyRyKUdGGVYHggj0Nedm2W08fhZ4Wp9paPs+j+TIqQU48rPPP2XvjjD8dfhda6hI8Y1ezxbalEvG2UD74HZXHzDt1HY16NXxr478Pap+wj8dofEOjxTXPg3W3KPAD8uw8tAT2dfvIT1A74avrTwR42034i+FrPWdIuku9Pvk3xuvUeqsOzA8EHkEV5nD+aVK0ZYLGaV6Wkl/Mukl3T/P1R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").getBytes("UTF-8"));
    		Blob blob = new javax.sql.rowset.serial.SerialBlob(BlobProxy.generateProxy(decodedString));
			works.setAfterPhoto(blob);
			works.setBeforePhoto(blob);
		} catch (SerialException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		works.setDate(new java.sql.Date(2018, 12, 12));
		works.setShowHomePage(true);
    	
    	List<Works> listWorks = new ArrayList<>();
    	listWorks.add(works);
		profile.setWorks(listWorks);
		profileRepository.save(profile);
    		
		
		works.setProfile(profile);
		Works workRetval = workRepository.save(works );
    	
    	    	
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
	      
	   	//RSS
			
		List<RssData> rssList = new ArrayList<>();
		ArrayList<RssData> subList = null;
	       
	    List<UserModel> listUser = new ArrayList<>();
						
	    for (User item : data) {
			listUser.add(userConverter.user2UserModel(item));
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
			
				   int counter = 0;
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
							userWrapperHome.setPicture(item.getPicture());
							
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
							profileWrapperHome.setAddress(item.getProfile().getAddress());
							profileWrapperHome.setSigned(item.getProfile().getSigned());
							profileWrapperHome.setPhone(item.getProfile().getPhone());
							
							profileWrapperHome.setSetAccessfirstTime(item.getProfile().isAccessfirstTime());
							profileWrapperHome.setPostCount(String.valueOf(getCountPost(item.getProfile().getId())));
							
							//RSS Temp
						//	String rssLink = "<a target=\'_blank\' href=\'"+subList.get(counter).getUri()+"\'>"+subList.get(counter++).getTitle()+"</a>";
						//	profileWrapperHome.setRssLink(rssLink );
							
							//Setting WorkeWrapper
							WorkWrapperHome worksWrapperHome = new WorkWrapperHome();
							
							if (item.getProfile().getWorks()!=null && !item.getProfile().getWorks().isEmpty()) {
							
							
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
								
								
							}
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
		 DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		if (listWorks!=null && !listWorks.isEmpty()) {
			for (Works item : listWorks) {
			  
	           String strDate = dateFormat.format(item.getDate());
				if (item.getBeforePhoto()!=null && !strDate.equalsIgnoreCase("3919-01-12")) {
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
		
		//RSS
		
		/*
		
		List<RssData> rssList = new ArrayList<>();
		ArrayList<RssData> subList = null;
			
		  try {
	            String url = "https://www.cibercuba.com/noticias/cibercuba/rss.xml";
	 
	            try (XmlReader reader = new XmlReader(new URL(url))) {
	                SyndFeed feed = new SyndFeedInput().build(reader);
	                 for (SyndEntry entry : feed.getEntries()) {
	                	
	                	RssData  rssData = new RssData();
	                	rssData.setTitle(entry.getTitle().trim());
	                	rssData.setDescription(entry.getDescription().getValue().trim());
	                	rssData.setUri(entry.getUri().trim());
	                	rssList.add(rssData);
	                	
	                }
	                
	            }
	          
	           
	            if (page==0) {
	            	subList = new ArrayList<RssData>(rssList.subList(0, 4));
	            	
	            }else {
	            	try {
	            		 subList = new ArrayList<RssData>(rssList.subList(4*page, 4*page+4));
		            	
					} catch (Exception e) {
						subList = new ArrayList<RssData>(rssList.subList(4*page, rssList.size()-1));
		            	
						System.out.println("Error : "+e.getMessage());
					}
	            }
	            
	        }  catch (Exception e) {
	            e.printStackTrace();
	        }		
		
		// END RSS
		
		*/
		
		
		List<UserModel> listUser = new ArrayList<>();
		List<UserWrapperHome> retval = new ArrayList<>();
		
		Page<User> pages = findAll(page);
		
//		System.out.println("Total Elements" + pages.getTotalElements());
//		System.out.println("Total Pages" + pages.getTotalPages());
		
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
		
			   int counter = 0;
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
						userWrapperHome.setPicture(item.getPicture());
						
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
						profileWrapperHome.setAddress(item.getProfile().getAddress());
						profileWrapperHome.setSigned(item.getProfile().getSigned());
						profileWrapperHome.setPhone(item.getProfile().getPhone());
						
						profileWrapperHome.setSetAccessfirstTime(item.getProfile().isAccessfirstTime());
						
						profileWrapperHome.setPostCount(String.valueOf(getCountPost(item.getProfile().getId())));
					
						
						/*
						//RSS 
					
						String rssLink = "<a target=\'_blank\' href=\'"+subList.get(counter).getUri()+"\'>"+subList.get(counter++).getTitle()+"</a>";
						profileWrapperHome.setRssLink(rssLink );
						*/
						
						//Setting WorkeWrapper
						WorkWrapperHome worksWrapperHome = new WorkWrapperHome();
						
						if (item.getProfile().getWorks()!=null && !item.getProfile().getWorks().isEmpty()) {
						
						
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
							
							
						}
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
				pf.setAccessfirstTime(false);
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
			
			//Update token
			String token = getToken();
			if (token!=null && !token.equals("")) {
			
				UserModel usrTokenTmp = userConverter.user2UserModel(userRepository.findByUsername(username));
				if (usrTokenTmp!=null) {
				User userSave = userConverter.userModel2User(usrTokenTmp);
				userSave.setToken(token);
				userRepository.save(userSave);
				}else {
					return flag;
				}
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

	@Override
	public List<PostModel> getAllPost(Long profileId) {
		
		List<PostModel> retval = new ArrayList<>();
		
		try {
			
			User user = userRepository.findById(profileId);
			
			List<Comment> postsList = postRepository.findAllByProfile(user.getProfile());
			
			for (Comment item : postsList) {
				
				retval.add(postConverter.post2postModel(item));
				
			}
			
		} catch (Exception e) {
			retval = null;
		}
				
		return retval;
	}

	@Override
	public PostModel sentPost(PostModel postModel) {
		PostModel retval = null;
		
		if (postModel!=null) {
			
			User user = userRepository.findById(postModel.getId());
		   //Setting values 
			Comment c = new Comment();
			c.setDate(postModel.getDate());
			c.setPost(postModel.getPost());
			c.setUsername(postModel.getUsername());
			c.setProfile(user.getProfile());
						
			Comment entity = postRepository.save(c);
			retval = postConverter.post2postModel(entity);
		}
		
		return retval;
	}

	@Override
	public int getCountPost(Long profileId) {
     int retval = 0 ;
		
		try {
			
			User user = userRepository.findById(profileId);
			
			List<Comment> postsList = postRepository.findAllByProfile(user.getProfile());
			
			retval = postsList.size();
			
		} catch (Exception e) {
			return retval;
		}
				
		return retval;
	}

	
			    
}
