package com.grassvsmower.wrappers;

import com.grassvsmower.entities.Profile;

public class UserWrapperHome {
	
private Long id;

	
	private String username;
	
	
    private String firstname;
    
    
    private String lastname;
	
	
	private boolean enabled;
	
	
    private String token;
    
    
    private String email;
    
    private String picture;
	
	
    private int tipoUsuario;
    
    private ProfileWrapperHome profile;
    
    
    public UserWrapperHome() {
		
	}


	public UserWrapperHome(Long id, String username, String firstname, String lastname, boolean enabled, String token,
			String email, int tipoUsuario, ProfileWrapperHome profile) {
		super();
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.enabled = enabled;
		this.token = token;
		this.email = email;
		this.tipoUsuario = tipoUsuario;
		this.profile = profile;
	}


	public ProfileWrapperHome getProfile() {
		return profile;
	}


	public void setProfile(ProfileWrapperHome profile) {
		this.profile = profile;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getFirstname() {
		return firstname;
	}


	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}


	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public String getToken() {
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public int getTipoUsuario() {
		return tipoUsuario;
	}


	public void setTipoUsuario(int tipoUsuario) {
		this.tipoUsuario = tipoUsuario;
	}


	public String getPicture() {
		return picture;
	}


	public void setPicture(String picture) {
		this.picture = picture;
	}    

}
