package com.grassvsmower.services;

import java.util.List;

import org.springframework.data.domain.Page;

import com.grassvsmower.entities.User;
import com.grassvsmower.model.PostModel;
import com.grassvsmower.model.ProfileModel;
import com.grassvsmower.model.UserModel;
import com.grassvsmower.model.WorksModel;
import com.grassvsmower.wrappers.HomeWrapper;
import com.grassvsmower.wrappers.UserWrapperHome;

public interface UserService {
	
	public UserModel register(UserModel usermodel, ProfileModel profileModel, List<WorksModel> worksModel);
    
    public List<UserWrapperHome> search (String name);
    
    public boolean checkToken();
    
    public UserModel getUsernameByToken();
    
    public Page<User> findAll(int page);   
    
    public UserModel getProfile (UserModel usermodel) ;
    
    public UserModel findByUsername(String username);
    
    public List<PostModel> getAllPost(Long profileId);
    
    public int getCountPost(Long profileId);
    
    public PostModel sentPost(PostModel postModel);

	public String getToken();
	
	public HomeWrapper getHomeProfile (int page);
	
	public boolean has_Token(String username);
	
	public User findById(Long id);
	
	public int setLike(Long id, String uid, boolean operation);

}
