package com.grassvsmower.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.grassvsmower.components.ContactConverter;
import com.grassvsmower.components.RequestServicesConverter;
import com.grassvsmower.components.UserConverter;
import com.grassvsmower.entities.Contact;
import com.grassvsmower.entities.RequestServices;
import com.grassvsmower.entities.User;
import com.grassvsmower.model.ContactModel;
import com.grassvsmower.model.RequestServicesModel;
import com.grassvsmower.model.UserModel;
import com.grassvsmower.repository.ContactRepository;
import com.grassvsmower.repository.RequestServicesRepository;
import com.grassvsmower.services.ContactService;
import com.grassvsmower.services.MailSender;
import com.grassvsmower.services.RequestServicesService;
import com.grassvsmower.services.UserService;
import com.grassvsmower.utils.Utils;

@Service("requestServicesServiceImpl")
public class RequestServicesServiceImpl implements RequestServicesService {
	
	@Autowired
    @Qualifier("requestServicesRepository")
    private RequestServicesRepository requestServicesRepository;
	
	@Autowired
    @Qualifier("mailSenderImpl")
    public MailSender mailSender;
       
    @Autowired
    @Qualifier("requestServicesConverter")
    private RequestServicesConverter requestServicesConverter;
    
    @Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;
    
    @Autowired
    @Qualifier("userConverter")
    private UserConverter userConverter;
    
    @Autowired
    @Qualifier("utils")
    private Utils utils;
    
   
	@Override
	public List<RequestServices> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RequestServicesModel sendRequest(RequestServicesModel requestServicesModel) {
		
		if (requestServicesModel!=null) {
			
			//Destiny
			User ursDestiny = userService.findById(new Long(requestServicesModel.getProfileid()));
			
			if (ursDestiny!=null) {
				
				RequestServices entity = requestServicesConverter.requestServicesModel2RequestServices(requestServicesModel);
				
				
				//applying Filter
				/*
				 String customTitle = profileModel.getTitle().replace("*", "");
					if(customTitle!=null && !customTitle.equalsIgnoreCase(""))
						pf.setTitle(customTitle.trim() );
					else
						pf.setTitle("My Title");
				  */
				
				String name = entity.getName().replace("*", "");
				if(name!=null && !name.equalsIgnoreCase("") && !utils.badWordFilter(name))
					entity.setName(name);
				else
					entity.setName("My Name");
				
				String lastname = entity.getLastName().replace("*", "");
				if(lastname!=null && !lastname.equalsIgnoreCase("") && !utils.badWordFilter(lastname))
					entity.setLastName(lastname);
				else
					entity.setLastName("My Last Name");
				
				
				String services = entity.getServices().replace("*", "");
				if(services!=null && !services.equalsIgnoreCase("") && !utils.badWordFilter(services))
					entity.setServices(services);
				else
					entity.setServices("My Services");
				
							
				RequestServices persist = requestServicesRepository.save(entity);
				RequestServicesModel reqServ = requestServicesConverter.requestServices2requestServicesModel(persist);
				
				if (reqServ!=null) {
				
					// Prepare the email
					String from = reqServ.getEmail();
					String to = ursDestiny.getEmail();
					String subject = "New Service Request";
					
					String body = "<strong>NAME: </strong>"+reqServ.getFirstname()+ " "+reqServ.getLastname()+" <br> "
		        		    + "<strong>PHONE:  </strong>"+ reqServ.getPhone()+" <br>"
		        		    + "<strong>EMAIL:  </strong>"+from+" <br>"
		        		    + "<strong>SERVICES:  </strong>"+reqServ.getServices()+" <br>";
					
					if (reqServ.getAddress()!=null && !reqServ.getAddress().isEmpty()) {
						body+="<strong>ADDRESS:  </strong>"+reqServ.getAddress();
					}
					
					
					 //Rel from : grassvsmower@gmail.com
			        mailSender.send("green.helix.yt@gmail.com", to, subject, body, true); 
			        System.out.println("****************  Receiving RequestServicesModel :  "+ requestServicesModel);
					
				}else {
					return null;
				}
				
			}else {
				return null;
			}	
			
		}
		return null;
	}

	
	
	@Override
	public RequestServicesModel findById(Long id) {
		RequestServices srm = requestServicesRepository.findById(id);
		return requestServicesConverter.requestServices2requestServicesModel(srm);
	}

}
