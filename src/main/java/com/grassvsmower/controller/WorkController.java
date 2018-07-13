package com.grassvsmower.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grassvsmower.components.ConstantsURL;
import com.grassvsmower.entities.Works;
import com.grassvsmower.services.ProfileService;
import com.grassvsmower.services.UserService;
import com.grassvsmower.services.impl.WorkServiceImpl;

@RestController
public class WorkController {
	
	@Autowired
    @Qualifier("profileServiceImpl")
    private ProfileService profileService;
	
	@Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;
	
	
	@Autowired
    @Qualifier("workServiceImpl")
    private WorkServiceImpl workServiceImpl;
	
	
	
	@GetMapping(value=ConstantsURL.DELETEWORKBYID)
    public ResponseEntity<?> deleteWorks (@RequestParam(name="id",required=false, defaultValue="null") String id){
		
		if (!userService.checkToken())
	     return new ResponseEntity<String>("Unauthorized",HttpStatus.OK );
		
		 long idw = Long.valueOf(id);
		
		 boolean retval = workServiceImpl.delete(idw);
		 return new ResponseEntity<Boolean>(retval,HttpStatus.OK);
		
    }
	
	@GetMapping(value=ConstantsURL.SETHOME)
    public ResponseEntity<?> setHomeWorks (@RequestParam(name="id",required=false, defaultValue="null") String id){
		
		if (!userService.checkToken())
	     return new ResponseEntity<String>("Unauthorized",HttpStatus.OK );
		
		 long idw = Long.valueOf(id);
		
		 Works retval = workServiceImpl.setHome(idw);
		 return new ResponseEntity<Works>(retval,HttpStatus.OK);
		
    }
	
	
	@GetMapping(value=ConstantsURL.ISFULLCUP)
    public ResponseEntity<?> isFullCups (@RequestParam(name="id",required=false, defaultValue="null") String id){
		
		if (!userService.checkToken())
	     return new ResponseEntity<String>("Unauthorized",HttpStatus.OK );
		
		 long idw = Long.valueOf(id);
		
		 boolean retval = workServiceImpl.isFullCupsToSetHome(idw);
		 return new ResponseEntity<Boolean>(retval,HttpStatus.OK);
		
    }

}
