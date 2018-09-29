package com.grassvsmower.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.grassvsmower.components.ConstantsURL;
import com.grassvsmower.components.EntityNotFoundException;
import com.grassvsmower.model.PostModel;
import com.grassvsmower.services.UserService;

@RestController
public class PostController {
	
		
	@Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;
	
	@GetMapping(value=ConstantsURL.GETALLPOSTS)
    public ResponseEntity<?> getAllPosts(@RequestParam(name="profileId", required=true, defaultValue="0") int profileId ){
    
		List<PostModel> retval = null;
		
		if (profileId<=0) {
			return new ResponseEntity<List<PostModel>>(retval,HttpStatus.OK);
		}
		
		retval = userService.getAllPost(new Long(profileId));
        return new ResponseEntity<List<PostModel>>(retval,HttpStatus.OK);
      
    }
	
		
	@PostMapping(value=ConstantsURL.SENDPOST)
    public ResponseEntity<?> sendPost (@Valid @RequestBody PostModel postModel){
		
		PostModel retval = null;
		
		if (!userService.checkToken())
		     return new ResponseEntity<String>("Unauthorized",HttpStatus.OK );
		
		if (postModel!=null) {			
			retval = userService.sentPost(postModel);						
		}else {
		   throw new EntityNotFoundException("PostModel->" + postModel);
		}
		
		return new ResponseEntity<PostModel>(retval,HttpStatus.OK);		
	}		
	
}
