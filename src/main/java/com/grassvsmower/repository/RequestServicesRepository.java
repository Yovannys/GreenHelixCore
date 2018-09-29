package com.grassvsmower.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.grassvsmower.entities.RequestServices;
import com.grassvsmower.entities.User;

@Repository("requestServicesRepository")
@Transactional
public interface RequestServicesRepository extends JpaRepository<RequestServices, Long> {
	
//	@Query("Select c from Contact c where c.name like %:name%")
//	public List<Contact> findByNameContaining(@Param("name") String name);
//	
//	@Query("Select c from Contact c where c.lastName like %:lastName%")
//	public List<Contact> findByLastNameContaining(@Param("lastName") String lastName);
//	
 	public List<RequestServices> findAll(); 
 	public RequestServices findById(Long id);

}
