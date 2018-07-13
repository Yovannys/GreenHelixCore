package com.grassvsmower.controller;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.grassvsmower.components.ConstantsURL;
import com.grassvsmower.components.EntityNotFoundException;
import com.grassvsmower.model.ProfileModel;
import com.grassvsmower.model.UserModel;
import com.grassvsmower.services.ProfileService;
import com.grassvsmower.services.UserService;
import com.grassvsmower.wrappers.WorkWrapperHome;
import com.grassvsmower.model.WorksModel;

@RestController
public class ProfController {
	
	@Autowired
    @Qualifier("profileServiceImpl")
    private ProfileService profileService;
	
	@Autowired
    @Qualifier("userServiceImpl")
    private UserService userService;
	
	

	@PostMapping(value=ConstantsURL.ADDWORKS)
    public ResponseEntity<?> setWorksToProfileByUser (@RequestBody UserModel usermodel){
		
		//ME quede aqui: Probar registrar una imagen en como bytesarray en la bd y luego desde angular
		
		List<WorksModel> worksModel = new ArrayList<WorksModel>();
		ProfileModel retval = profileService.setWorksToProfileByUser(usermodel, worksModel);
		
		return new ResponseEntity<ProfileModel>(retval,HttpStatus.OK);
		
    }
	
	
	@PostMapping(value=ConstantsURL.SAVEPROFILE)
    public ResponseEntity<?> saveProfile (@Valid @RequestBody ProfileModel profileModel){
		
		if (!userService.checkToken())
	     return new ResponseEntity<String>("Unauthorized",HttpStatus.OK );
		
		ProfileModel retval = profileService.update(profileModel);
		if (retval==null)
		throw new EntityNotFoundException("ProfileModel->" + profileModel);
		
		return new ResponseEntity<ProfileModel>(retval,HttpStatus.OK);
		
    }
	
	@GetMapping(value=ConstantsURL.GETWORKSBYUSERNAME)
    public ResponseEntity<?> getworks (@RequestParam(name="username",required=false, defaultValue="null") String username){
		
		if (!userService.checkToken())
	     return new ResponseEntity<String>("Unauthorized",HttpStatus.OK );
		
		 WorkWrapperHome[] retval = profileService.getworks(username);
		 if (retval==null)
		 throw new EntityNotFoundException("username->" + username);	 
		 
		 return new ResponseEntity<WorkWrapperHome[]>(retval,HttpStatus.OK);
		
    }
	
	
	@PostMapping(value=ConstantsURL.UPLOADWORKS)
	public  ResponseEntity<?> handleFileUpload(@RequestParam("file") MultipartFile file) {
		
		System.out.println("Entre " );
		boolean retval = false;
		
		if (!userService.checkToken())
	     return new ResponseEntity<String>("Unauthorized",HttpStatus.OK );
		
		
			try {
			
				UserModel userModel = userService.getUsernameByToken();
				
				List<WorksModel> worksModel = new ArrayList<>();
			             
		    	WorksModel img = new WorksModel();
				    	
		    	byte[] bytes = file.getBytes();
		    	Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
		    	
		    	img.setAfterPhoto(blob);
		    	//img.setBeforePhoto(blob);
		    			    	
		    	String mydate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		    	LocalDate localDate = LocalDate.parse(mydate);
		        Date date = Date.valueOf(localDate);
		    	img.setDate(date);
		    	
		    	worksModel.add(img);
						
				ProfileModel pf = profileService.setWorksToProfileByUser(userModel, worksModel);
				
				if (pf!=null) {
					retval = true;
				}else {
					 throw new EntityNotFoundException("File->" + file);
				}
				 
		} catch (Exception e) {
			retval = false;	 
			
		}
		
		
		 return new ResponseEntity<Boolean>(retval,HttpStatus.OK);
	}

}
