package com.grassvsmower.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.grassvsmower.components.ContactConverter;
import com.grassvsmower.entities.Contact;
import com.grassvsmower.model.ContactModel;
import com.grassvsmower.model.UserModel;
import com.grassvsmower.repository.ContactRepository;
import com.grassvsmower.services.ContactService;
import com.grassvsmower.services.MailSender;

@Service("contactServiceImpl")
public class ContactServiceImpl implements ContactService {
	
	@Autowired
    @Qualifier("contactRepository")
    private ContactRepository contactRepository;
	
	@Autowired
    @Qualifier("mailSenderImpl")
    public MailSender mailSender;
       
    @Autowired
    @Qualifier("contactConverter")
    private ContactConverter contactConverter;
    
    @Autowired
    @Qualifier("userServiceImpl")
    public UserServiceImpl userServiceImpl;

	@Override
	public ContactModel register(ContactModel contactModel) {
		 Contact contact = contactRepository.save(contactConverter.contactModel2Contact(contactModel));
		 ContactModel retval = contactConverter.contact2contactModel(contact);
		 
		 // Check this function
		 //sendEmail(contactModel);
		 return retval;
	}
	
	 public void sendEmail(ContactModel contactModel){
        
		UserModel user = userServiceImpl.getUsernameByToken();
        String from = user.getEmail();
		String subject = "New Service Request";
        String body = "<strong>NAME: </strong>"+contactModel.getFirstname()+ " "+contactModel.getLastname()+" <br> "
        		    + "<strong>PHONE:  </strong>"+ contactModel.getPhone()+" <br>"
        		    + "<strong>EMAIL:  </strong>"+from+" <br>"
        		    + "<strong>SUBJECT:  </strong>"+contactModel.getSubject()+" <br>"
                    + "<strong>MESSAGE:  </strong>"+contactModel.getMessage();
       
        //Rel from : grassvsmower@gmail.com
        mailSender.send(from, "yovanotti2004@gmail.com", subject, body, true); 
        
    }

}
