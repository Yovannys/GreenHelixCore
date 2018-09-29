package com.grassvsmower.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.grassvsmower.components.ConstantsURL;
import com.grassvsmower.components.ContactConverter;
import com.grassvsmower.components.EntityNotFoundException;
import com.grassvsmower.components.RequestServicesConverter;
import com.grassvsmower.model.ContactModel;
import com.grassvsmower.model.RequestServicesModel;
import com.grassvsmower.services.ContactService;
import com.grassvsmower.services.RequestServicesService;

@RestController
public class RequestServicesController {
	
	@Autowired
    @Qualifier("requestServicesServiceImpl")
    private RequestServicesService requestServicesService;
	
	@Autowired
    @Qualifier("requestServicesConverter")
    private RequestServicesConverter requestServicesConverter;
	
		
	
	@PostMapping(value=ConstantsURL.SENDREQUESTSERVICES)
    public ResponseEntity<?> sendContact (@Valid @RequestBody RequestServicesModel requestServicesModel){
		
		RequestServicesModel retval = new RequestServicesModel();
		
		if (requestServicesModel!=null) {
			
			retval = requestServicesService.sendRequest(requestServicesModel);
						
		}else {
			  throw new EntityNotFoundException("RequestServicesModel->" + requestServicesModel);
		}
		
		return new ResponseEntity<RequestServicesModel>(retval,HttpStatus.OK);
		
	}	
	

}
