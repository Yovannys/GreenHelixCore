/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grassvsmower.services.impl;

import java.sql.Blob;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
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
import com.grassvsmower.services.WorkService;
import com.grassvsmower.utils.Utils;
import com.grassvsmower.wrappers.WorkWrapperHome;

/**
 *
 * @author Adriana
 */

@Service("workServiceImpl")
public class WorkServiceImpl implements WorkService{
	
	private Logger LOG = LoggerFactory.getLogger(this.getClass());
    
    
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
    @Qualifier("userServiceImpl")
    private UserService userService;
    
    @Autowired
    @Qualifier("utils")
    private Utils utils;
   
    
    @Override
	public List<Works> findAllByProfile(Profile profile) {
    	return workRepository.findAllByProfile(profile);
		
	}
    
	@Override
	public Works findById(Long id) {
		return workRepository.findById(id);
	}

	@Override
	public boolean delete(Long id) {
		boolean retval = false;
		
		try {
			Works wk = findById(id);
			if (wk!=null) {
				workRepository.delete(id);
				retval = true;
			}
			
		} catch (Exception e) {
			retval = false;
		}
		return retval;
		
		
	}

	@Override
	public Works canSetHome (Long id) {
		
		Works retval = null;
		
		try {
			retval = findById(id);
			if (retval!=null) {
				List<Works> workList = workRepository.findAllByProfile(retval.getProfile());
				if (workList!=null && !workList.isEmpty()) {
					
					 //Remove all values where showHome is false
			    	 
		    	      CollectionUtils.filter(workList, new Predicate() {
		    	        @Override
		    	              public boolean evaluate(Object arg0) {
		    	        	          Works comp = (Works) arg0;
		    	                      return (comp.isShowHomePage() );
		    	              }
		    	        });
		    	      
		    	      if (workList!=null && !workList.isEmpty() && (workList.size() >= 0 && workList.size() < 2)) {
		    	    	  return retval;
		    	      }
					
						
				}
				
			}
			
		} catch (Exception e) {
			return retval;
		}
		
		
		return retval;
	}
	
	
	public boolean onValidateShowHome(long idprof) {
		boolean retval = false;
		
		 User usr = userService.findById(idprof);
		 
		 if (usr!=null) {
			 
			 List<Works> workList = workRepository.findAllByProfile(usr.getProfile());
				if (workList!=null && !workList.isEmpty()) {
					
					if (ifAllShowHomeisFalse(workList.get(0))) {
						
						Works workToSave = findById(workList.get(0).getId());
						workToSave.setShowHomePage(true);
						
						Works ws = workRepository.save(workToSave);
						if (ws!=null) {
							retval = true;
						}
					}
					
				}
			 
		 }
		
		return retval;
	}
	
   public boolean isFullCupsToSetHome (long idProfile) {
		
		boolean retval = false;
		
		 User usr = userService.findById(idProfile);
		
		if (usr!=null) {
			List<Works> workList = workRepository.findAllByProfile(usr.getProfile());
			if (workList!=null && !workList.isEmpty()) {
				
				 //Remove all values where showHome is false
		    	 
	    	      CollectionUtils.filter(workList, new Predicate() {
	    	        @Override
	    	              public boolean evaluate(Object arg0) {
	    	        	          Works comp = (Works) arg0;
	    	        	          
	    	        	          DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			        	          String strDate = dateFormat.format(comp.getDate());
			                      return (comp.isShowHomePage() && !strDate.equalsIgnoreCase("3919-01-12") );
	    	              }
	    	        });
	    	      
	    	      if (workList!=null && !workList.isEmpty() && workList.size()==2) {
	    	    	  retval = true;
	    	      }	else {
	    	    	  return retval;
	    	      }		
					
			}
			
		}
		
		return retval;
	}
   
   //
   
   public boolean isOnlyExistDefaultWorkWithShowHomeTrue (long idProfile) {
		
		boolean retval = false;
		
		 User usr = userService.findById(idProfile);
		
		if (usr!=null) {
			List<Works> workList = workRepository.findAllByProfile(usr.getProfile());
			if (workList!=null && !workList.isEmpty()) {
				
				 //Remove all values where showHome is false
		    	 
	    	      CollectionUtils.filter(workList, new Predicate() {
	    	              @Override
	    	              public boolean evaluate(Object arg0) {
	    	        	          Works comp = (Works) arg0;
	    	        	          
	    	        	          DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			        	          String strDate = dateFormat.format(comp.getDate());
			                      return (comp.isShowHomePage() && strDate.equalsIgnoreCase("3919-01-12") );
	    	              }
	    	        });
	    	      
	    	      if (workList!=null && !workList.isEmpty() && workList.size()==1) {
	    	    	  retval = true;
	    	      }	else {
	    	    	  return retval;
	    	      }		
					
			}
			
		}
		
		return retval;
	}
   
   //
	
	private boolean ifAllShowHomeisFalse (Works workParam) {
		
		boolean retval = false;
		
		if (workParam!=null) {
			List<Works> workList = workRepository.findAllByProfile(workParam.getProfile());
			if (workList!=null && !workList.isEmpty()) {
				
				 //Remove all values where showHome is false
		    	 
	    	      CollectionUtils.filter(workList, new Predicate() {
	    	        @Override
	    	              public boolean evaluate(Object arg0) {
	    	        	          Works comp = (Works) arg0;
	    	                      return (comp.isShowHomePage() );
	    	              }
	    	        });
	    	      
	    	      if (workList!=null && !workList.isEmpty()) {
	    	    	 return retval;
	    	      }	else {
	    	    	  retval = true;
	    	      }		
					
			}
			
		}
		
		return retval;
	}
	
	private Works getWorkWithShowHomeTrue (Works workParam) {
		
		Works retval = null;
		
		if (workParam!=null) {
			
		  List<Works> workList = workRepository.findAllByProfile(workParam.getProfile());
			if (workList!=null && !workList.isEmpty()) {
				
				 //Remove all values where showHome is false
		    	 
	    	      CollectionUtils.filter(workList, new Predicate() {
	    	        @Override
	    	              public boolean evaluate(Object arg0) {
	    	        	          Works comp = (Works) arg0;
	    	                      return (comp.isShowHomePage() );
	    	              }
	    	        });
	    	      
	    	      if (workList!=null && !workList.isEmpty()) {
	    	    	  retval = workList.get(0);
	    	    	  return retval;
	    	      }			
					
			}
			
		}
		
		return retval;
	}
	
	private Works searchAnotherWork(Works works) {
		Works retval = null;
		
		final Long id = works.getId();
		
		if (works!=null) {
			List<Works> workList = workRepository.findAllByProfile(works.getProfile());
			if (workList!=null && !workList.isEmpty()) {
				
				 //Remove all values where showHome is false
		    	 
	    	      CollectionUtils.filter(workList, new Predicate() {
	    	        @Override
	    	              public boolean evaluate(Object arg0) {
	    	        	          Works comp = (Works) arg0;
	    	                      return (comp.isShowHomePage() && comp.getId()!= id);
	    	              }
	    	        });
	    	      
	    	      if (workList!=null && !workList.isEmpty()) {
	    	    	  retval = workList.get(0);
	    	    	  return retval;
	    	      }			
					
			}
		}
		
		return retval;
	}
	
	@Override
	public Works setHome(Long id) {
		Works retval = null;
		try {
			Works wk = canSetHome(id);
			
				if(wk!=null) {
					
					//Validate if is possible set Home
					// Search the row with SetshowHome is true
					
					//If there are 2 rows with SetshowHome is true
					if (isFullCupsToSetHome (wk.getProfile().getId())) {
						
						Works workToSave = findById(wk.getId());
						//Check if exist before
						if (workToSave.getBeforePhoto()!=null) {
							
							workToSave.setShowHomePage(false);
							workToSave.setBeforePhoto(null);
							retval = workRepository.save(workToSave);
							
							return retval;
														
						}else {
							workToSave.setShowHomePage(false);
							retval = workRepository.save(workToSave);
							
							Works anotherWork = searchAnotherWork(workToSave);
							Works anotherWorkToSave = findById(anotherWork.getId());
							anotherWorkToSave.setBeforePhoto(null);
							retval = workRepository.save(anotherWorkToSave);
							
							return retval;
						}
					}
					
					//If all rows are set ShowHome = false
					boolean ifAllRowsWithShowHomeisFalse = ifAllShowHomeisFalse(wk);
					if (ifAllRowsWithShowHomeisFalse) {
						
						//Set ShowHomeTrue
						Works rowWithSetHomeTrueToSave = workRepository.findById(wk.getId());
						rowWithSetHomeTrueToSave.setShowHomePage(true);
						retval = workRepository.save(rowWithSetHomeTrueToSave);
						return retval;
					}
					
					//IF want to set all rows with ShowHome = false
					
					Works anotherWork = searchAnotherWork(wk);
					if (anotherWork==null) {
						Works rowWithSetHomeTrueToSave = workRepository.findById(wk.getId());
						rowWithSetHomeTrueToSave.setShowHomePage(false);
						rowWithSetHomeTrueToSave.setBeforePhoto(null);
						retval = workRepository.save(rowWithSetHomeTrueToSave);
						return retval;
						
					}
					
					//If at least there is 1 file with set ShowHome = true
															
					Works rowWithSetHomeTrue = getWorkWithShowHomeTrue (wk);
					if (rowWithSetHomeTrue!=null) {
						
						//previous
						Works copyingRowBefore = workRepository.findById(rowWithSetHomeTrue.getId());
						
						//new one
						Works rowWithSetHomeTrueToSave = workRepository.findById(wk.getId());
						
					  if (isOnlyExistDefaultWorkWithShowHomeTrue(wk.getProfile().getId())) {
						 
						 //Set default ShowHome false
						  copyingRowBefore.setShowHomePage(false);
						  workRepository.save(copyingRowBefore);
						  
						 //Set ShowHomeTrue
						 rowWithSetHomeTrueToSave.setShowHomePage(true);
						 retval = workRepository.save(rowWithSetHomeTrueToSave);
						 return retval;
						
					  }else {
						  
						 copyingRowBefore.setBeforePhoto(rowWithSetHomeTrueToSave.getAfterPhoto());
						 workRepository.save(copyingRowBefore);
						 
						 rowWithSetHomeTrueToSave.setShowHomePage(true);
						 retval = workRepository.save(rowWithSetHomeTrueToSave);
						 return retval;
					  }
																		
					}
					
				}
			
		} catch (Exception e) {
			return retval;
		}
		return retval;
	}

	@Override
	public List<WorkWrapperHome> findAll(int idprofile) {
		
		List<WorkWrapperHome> retval = new ArrayList<WorkWrapperHome>();
		
		Long id= new Long(idprofile);
		
		User usr = userService.findById(id);		
		List<Works> workList = usr.getProfile().getWorks();
		
		if (workList!=null && !workList.isEmpty()) {
			for (Works item : workList) {
			
				//Setting WorkeWrapper
				WorkWrapperHome worksWrapperHome = new WorkWrapperHome();
				
				if (item.getAfterPhoto()!=null) {
					Blob photo = item.getAfterPhoto();
					byte[] byteArr = utils.blobToByte(photo );					
					String photoString = Base64.getEncoder().withoutPadding().encodeToString(byteArr);
					worksWrapperHome.setAfterPhoto(photoString );
				}
				else {
					worksWrapperHome.setAfterPhoto(null);
				}
					
				if(item.getBeforePhoto()!=null) {
					worksWrapperHome.setBeforePhoto(Base64.getEncoder().withoutPadding().encodeToString(utils.blobToByte(item.getBeforePhoto())));
				}
				else {
					worksWrapperHome.setBeforePhoto(null);	
				}			
				
				worksWrapperHome.setDate(item.getDate());
				worksWrapperHome.setId(item.getId());
				worksWrapperHome.setShowHomePage(item.isShowHomePage());
				retval.add(worksWrapperHome);		
				
			}
		}else {
			return null;
		}
		
		
		if (!onlyDefaultWorks(retval)) {

			//Remove default work
			
		      CollectionUtils.filter(retval, new Predicate() {
		        @Override
		              public boolean evaluate(Object arg0) {
		        	  WorkWrapperHome comp = (WorkWrapperHome) arg0;
		        	         
		        	          DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		        	          String strDate = dateFormat.format(comp.getDate());
		                      return (!strDate.equalsIgnoreCase("3919-01-12") );
		              }
		        });
		}
		
		
		//Order desc
		Collections.reverse(retval);
				
		//WorkWrapperHome[] workWrapperHomeArr = retval.toArray(new WorkWrapperHome[retval.size()]);	
			
		return retval;
		
		
	}

	private boolean onlyDefaultWorks(List<WorkWrapperHome> listHome) {
		boolean retval = false;
				
		if (listHome.size()==1) {
			retval = true;
		}
		
		return retval;
	}		
    	 
}
