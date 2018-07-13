package com.grassvsmower.controller;

import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grassvsmower.components.ConstantsURL;
import com.grassvsmower.components.EntityNotFoundException;
import com.grassvsmower.components.ProfileConverter;
import com.grassvsmower.components.WorksConverter;
import com.grassvsmower.entities.Works;
import com.grassvsmower.model.Response;
import com.grassvsmower.model.UserModel;
import com.grassvsmower.model.WorksModel;
import com.grassvsmower.services.UserService;
import com.grassvsmower.wrappers.HomeWrapper;
import com.grassvsmower.wrappers.UserWrapperHome;

@RestController
public class AuthenticateController {
	
	@Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;
	
	@Autowired
    @Qualifier("profileConverter")
    private ProfileConverter profileConverter;
	
	@Autowired
    @Qualifier("worksConverter")
    private WorksConverter worksConverter;
	
	@GetMapping(value=ConstantsURL.GETSEARCHHOME)
    public ResponseEntity<?> searchProducts(@RequestParam(name="crit",required=false, defaultValue="null") String crit ){
       
     /* if (!userService.checkToken())
       return new ResponseEntity<String>("Unauthorized",HttpStatus.OK ); */
		
		List<UserWrapperHome> retval = null;
           
       if (!crit.equalsIgnoreCase("null") && !crit.isEmpty())  {
        retval =  userService.search(crit.trim());
        
        if (retval==null)
        throw new EntityNotFoundException("Crit->" + crit);
       
       }
        
      return new ResponseEntity<List<UserWrapperHome>>(retval,HttpStatus.OK);
    }
	
	@GetMapping(value=ConstantsURL.SETLIKE)
    public ResponseEntity<?> setLike(@RequestParam(name="id", required=false, defaultValue="null") long id,
    								 @RequestParam(name="uid", required=false, defaultValue="uid") String uid){
    
	 int retval = userService.setLike(id, uid, true);
	 	 
	 return new ResponseEntity<Integer>(retval,HttpStatus.OK);
	       
    }
	
	@GetMapping(value=ConstantsURL.SETUNLIKE)
    public ResponseEntity<?> setUnLike(@RequestParam(name="id", required=false, defaultValue="null") long id,
    								 @RequestParam(name="uid", required=false, defaultValue="uid") String uid){
    
	 int retval = userService.setLike(id, uid, false);
	 
	 return new ResponseEntity<Integer>(retval,HttpStatus.OK);
	       
    }
	
	
	@GetMapping(value=ConstantsURL.GETPROFILESHOME)
    public ResponseEntity<?> getProfileHome(@RequestParam(name="page", required=false, defaultValue="0") int page ){
		
		boolean validation = true;
		HomeWrapper retval = null;
		
		if (page < 0) {
			validation = false;	
		}else {
			 retval = userService.getHomeProfile(page);
			if (retval==null) {
				validation = true;
			}
		}
        
		if (!validation) {
			return new ResponseEntity<String>("No Content",HttpStatus.NO_CONTENT ); 
		}  
         
        return new ResponseEntity<HomeWrapper>(retval,HttpStatus.OK);
        
	 }
	

	@PostMapping(value=ConstantsURL.GETPROFILE)
    public ResponseEntity<?> getProfile (@RequestBody UserModel usermodel){
		
		UserModel retval = null;
		
		if (usermodel!=null) {
			retval = userService.getProfile(usermodel);
		}else {
			  throw new EntityNotFoundException("Usermodel->" + usermodel);
		}
		
		return new ResponseEntity<UserModel>(retval,HttpStatus.OK);
		
	}
	
	
	@GetMapping(value=ConstantsURL.FINDBYUSERNAME)
    public ResponseEntity<?> findByUserName(@RequestParam(name="username",required=false, defaultValue="null") String username ){
    
	  UserModel retval = null;
	  
	  if (!userService.has_Token(username))
		return new ResponseEntity<String>("Unauthorized",HttpStatus.OK );
       
	  if (!username.equalsIgnoreCase("null") && !username.isEmpty())  {
         retval = userService.findByUsername(username);        
       }else {
    	    throw new EntityNotFoundException("Username->" + username);
       }
       
       return new ResponseEntity<UserModel>(retval,HttpStatus.OK);
      
    }
	
	@GetMapping(value=ConstantsURL.HELLO)
    public ResponseEntity<?> hello(@PathVariable String name ){
        List<Response> list = new ArrayList<>();
        Response retval = new Response();
        retval.setDesc("Hello:"+name);
        retval.setCode(1);
        list.add(retval);
       
        return new ResponseEntity<>(list,HttpStatus.OK);
    }

}
