/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grassvsmower;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 *
 * @author Adriana
 */

//PROD:

@SpringBootApplication
public class App extends SpringBootServletInitializer {
  
  public static void main(String[] args) {
      SpringApplication.run(applicationClass, args);
  }

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
      return application.sources(applicationClass);
  }

  private static Class<App> applicationClass = App.class;
}


//DEV:

//@SpringBootApplication
////@EnableJpaAuditing
//public class App implements CommandLineRunner{
//     
//    private final  Logger log = LoggerFactory.getLogger(this.getClass());
//    
//    
//    
//    @Autowired
//    @Qualifier("workServiceImpl")
//    private WorkServiceImpl workServiceImpl;
//    
//    @Autowired
//    @Qualifier("contactServiceImpl")
//    private ContactServiceImpl contactServiceImpl;
//    
//    @Autowired
//    @Qualifier("userServiceImpl")
//    private UserServiceImpl userServiceImpl;
//    
//    @Autowired
//    @Qualifier("profileServiceImpl")
//    private ProfileServiceImpl profileServiceImpl;
//    
//    @Autowired
//    @Qualifier("loadImages")
//    private LoadImages loadImages;
//    
//    @Autowired
//    @Qualifier("profileConverter")
//    private ProfileConverter profileConverter;
//       
//    public static void main(String[] args) {
//            SpringApplication.run(App.class, args);
//    }     
//
//    @Override
//    public void run(String... strings) throws Exception {
//    	
//    	 log.info("******** YOVANOTTII******************");
//    	
//    	List<byte[]> imgAsByteArr = loadImages.show();
//    	
//        UserModel userModel = null;
//        
//        userModel = userServiceImpl.findByUsername("yovanotti2006");
//                
//        List<WorksModel> worksModel = new ArrayList<>();
//       
//        for (byte[] items : imgAsByteArr) {
//        	WorksModel img = new WorksModel();
//        	Blob blob = new javax.sql.rowset.serial.SerialBlob(items);
//        	img.setAfterPhoto(blob);
//        	img.setBeforePhoto(blob);
//        	worksModel.add(img );
//		}
//        
//        
//       
//		//Works wk = workServiceImpl.findById(2L);
//		
//		
//		// List<Works> wkList = workServiceImpl.findAllByProfile(wk.getProfile());
//		
//		 //System.out.println(wkList.size());
//       
//        
//     // Profile p = userModel.getProfile();
//     // p.setLanguage("LANG");
//       
//   //   profileServiceImpl.update(profileConverter.profile2profileModel(p));
//        
//       // profileServiceImpl.getworks("yovanotti2004");
//        
//		//profileServiceImpl.setWorksToProfileByUser(userModel, worksModel);
//        
//       // userServiceImpl.getHomeProfile ();
//        
//       // userServiceImpl.search("SuperYard");	
//        
//       // userServiceImpl.has_Token();
//        
//        
//		
//       
//    }
 
    
//}
