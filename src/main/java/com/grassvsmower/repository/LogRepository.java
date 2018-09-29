package com.grassvsmower.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.grassvsmower.entities.Log;

@Repository("logRepository")
@Transactional
public interface LogRepository extends JpaRepository<Log, Long> {
	
	

}
