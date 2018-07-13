/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grassvsmower.repository;

import com.grassvsmower.entities.User;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Adriana
 */
@Repository("userRepository")
@Transactional
public interface UserRepository extends JpaRepository<User, Long>{

	    public User findByUsername(String username);
	    public User findByToken(String password);   
	    public List<User> findAll();
	    public User findById(Long id);
	    
	    @Query("SELECT u FROM User u WHERE (u.profile.title LIKE %:crit% ) OR (u.profile.description like %:crit%) ")
	    List<User> findByCriteriaContaining(@Param("crit") String crit);
	     
}
