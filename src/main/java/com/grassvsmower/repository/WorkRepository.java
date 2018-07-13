/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.grassvsmower.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.grassvsmower.entities.Profile;
import com.grassvsmower.entities.Works;

/**
 *
 * @author Adriana
 */
@Repository("workRepository")
@Transactional
public interface WorkRepository extends JpaRepository<Works, Long>{

	public List<Works> findAllByProfile(Profile profile);
	
	public Works findById(Long id);		
	
}
