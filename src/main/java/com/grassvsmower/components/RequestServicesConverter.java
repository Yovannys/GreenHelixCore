package com.grassvsmower.components;

import org.springframework.stereotype.Component;

import com.grassvsmower.entities.RequestServices;
import com.grassvsmower.model.RequestServicesModel;

@Component("requestServicesConverter")
public class RequestServicesConverter {
	
	public RequestServices requestServicesModel2RequestServices(RequestServicesModel requestServicesModel){

		RequestServices requestServices = null;
        
        if (requestServicesModel!=null) {
        	requestServices = new RequestServices();
        	requestServices.setId(requestServicesModel.getId());
        	requestServices.setPhone(requestServicesModel.getPhone());
        	requestServices.setEmail(requestServicesModel.getEmail());
        	requestServices.setAddress(requestServicesModel.getAddress());
        	requestServices.setLastName(requestServicesModel.getLastname());
        	requestServices.setName(requestServicesModel.getName());
        	requestServices.setProfileid(requestServicesModel.getProfileid());
        	requestServices.setServices(requestServicesModel.getServices());
        	requestServices.setDate(requestServicesModel.getDate());
		
        }
		return requestServices;
		
	}
	
	public RequestServicesModel requestServices2requestServicesModel(RequestServices requestServices){
		
		RequestServicesModel requestServicesModel = null;
		
		if (requestServices!=null) {
		
			requestServicesModel = new RequestServicesModel();
			requestServicesModel.setId(requestServices.getId());
			requestServicesModel.setPhone(requestServices.getPhone());
			requestServicesModel.setEmail(requestServices.getEmail());
			requestServicesModel.setAddress(requestServices.getAddress());
			requestServicesModel.setLastname(requestServices.getLastName());
			requestServicesModel.setName(requestServices.getName());
			requestServicesModel.setProfileid(requestServices.getProfileid());
			requestServicesModel.setServices(requestServices.getServices());
			requestServicesModel.setDate(requestServices.getDate());
		
		}
		return requestServicesModel;
	}

}
