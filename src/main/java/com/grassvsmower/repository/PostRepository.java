package com.grassvsmower.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.grassvsmower.entities.Comment;
import com.grassvsmower.entities.Profile;

@Repository("postRepository")
@Transactional
public interface PostRepository extends JpaRepository<Comment, Long> {
	
	public List<Comment> findAllByProfile(Profile profile ); 

}
