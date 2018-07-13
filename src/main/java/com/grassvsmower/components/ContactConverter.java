package com.grassvsmower.components;

import org.springframework.stereotype.Component;

import com.grassvsmower.entities.Contact;
import com.grassvsmower.model.ContactModel;

@Component("contactConverter")
public class ContactConverter {
	
	public Contact contactModel2Contact(ContactModel contactModel){

        Contact contact = null;
        
        if (contactModel!=null) {
	        contact = new Contact();
			contact.setSubject(contactModel.getSubject());
			contact.setId(contactModel.getId());
			contact.setLastName(contactModel.getLastname());
			contact.setName(contactModel.getFirstname());
			contact.setPhone(contactModel.getPhone());
		    contact.setMessage(contactModel.getMessage());
			contact.setEmail(contactModel.getEmail());
		
        }
		return contact;
		
	}
	
	public ContactModel contact2contactModel(Contact contact){
		
		ContactModel contactModel = null;
		
		if (contact!=null) {
		
			contactModel = new ContactModel();
			contactModel.setSubject(contact.getSubject());
			contactModel.setId(contact.getId());
			contactModel.setLastname(contact.getLastName());
			contactModel.setFirstname(contact.getName());
			contactModel.setPhone(contact.getPhone());
			contactModel.setMessage(contact.getMessage());
			contactModel.setEmail(contact.getEmail());
		
		}
		return contactModel;
	}

}
