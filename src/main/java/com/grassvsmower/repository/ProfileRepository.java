/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grassvsmower.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.grassvsmower.entities.Contact;
import com.grassvsmower.entities.Profile;

/**
 *
 * @author Adriana
 */
@Repository("profileRepository")
@Transactional
public interface ProfileRepository extends JpaRepository<Profile, Long>{
    
	@Query("Select p from Profile p where p.user.id = :userid")
	public Profile findByUserId(@Param("userid") Long userid);
	
}
