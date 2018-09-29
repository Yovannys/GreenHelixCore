/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grassvsmower.services.impl;


import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.grassvsmower.services.MailSender;

/**
 *
 * @author Adriana
 */
@Service("mailSenderImpl")
public class MailSenderImpl implements MailSender{
    
    @Autowired
    JavaMailSender javaMailSender;
    
   private static final Log LOGGER =  LogFactory.getLog(MailSenderImpl.class);
    
   @Override
   public boolean send(String from, String to, String subject, String text, final boolean isHtmlMail) {
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		
		boolean retval = false;
		
		try {
	   	    MimeMessageHelper mailMsg = new MimeMessageHelper(mimeMessage);
	   	    mailMsg.setFrom(from);
		    mailMsg.setTo(to);
		    mailMsg.setSubject(subject);
		    
		    if (isHtmlMail) {
		    	mailMsg.setText("<html><body>" + text + "</html></body>", true);
            } else {
            	mailMsg.setText(text);
            }
		  
	        javaMailSender.send(mimeMessage);
	        retval = true;
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			return retval;
		}
		 return retval;
	}
    
}

// ID: green.helix.yt@gmail.com
// PASS :myBusiness*.2018

//# Gmail account is highly secured. So Email is blocked. The solution is to make the gmail account less secure:
//#– Login to Gmail.
//#– Access https://www.google.com/settings/security/lesssecureapps
//#– Select “Turn on”
//#      
    