package com.grassvsmower.services;

import java.util.List;

import com.grassvsmower.entities.RequestServices;
import com.grassvsmower.entities.User;
import com.grassvsmower.model.RequestServicesModel;

public interface RequestServicesService {
	
	public List<RequestServices> findAll(); 
	public RequestServicesModel sendRequest(RequestServicesModel requestServicesModel);
	public RequestServicesModel findById(Long id);

}
