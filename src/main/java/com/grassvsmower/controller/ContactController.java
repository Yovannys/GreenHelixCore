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
import com.grassvsmower.model.ContactModel;
import com.grassvsmower.services.ContactService;

@RestController
public class ContactController {
	
	@Autowired
    @Qualifier("contactServiceImpl")
    private ContactService contactService;
	
	@Autowired
    @Qualifier("contactConverter")
    private ContactConverter contactConverter;
	
		
	
	@PostMapping(value=ConstantsURL.SENDCONTACT)
    public ResponseEntity<?> sendContact (@Valid @RequestBody ContactModel contactModel){
		
		ContactModel retval = null;
		
		if (contactModel!=null) {
			
			retval = contactService.register(contactModel);
						
		}else {
			  throw new EntityNotFoundException("Contactmodel->" + contactModel);
		}
		
		return new ResponseEntity<ContactModel>(retval,HttpStatus.OK);
		
	}	
	

}
