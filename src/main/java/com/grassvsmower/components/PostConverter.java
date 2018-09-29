package com.grassvsmower.components;

import org.springframework.stereotype.Component;

import com.grassvsmower.entities.Comment;
import com.grassvsmower.model.PostModel;

@Component("postConverter")
public class PostConverter {
	
	public Comment postModel2Post(PostModel postModel){

		Comment comment = null;
        
        if (postModel!=null) {
	        
        	comment = new Comment();
        	comment.setDate(postModel.getDate());
        	comment.setId(postModel.getId());
        	comment.setPost(postModel.getPost());
        	comment.setUsername(postModel.getUsername());
		
        }
		return comment;
		
	}
	
	public PostModel post2postModel(Comment post){
		
		PostModel postModel = null;
		
		if (post!=null) {
			postModel = new PostModel();
			postModel.setDate(post.getDate());
			postModel.setId(post.getId());
			postModel.setPost(post.getPost());
			postModel.setUsername(post.getUsername());
		
		}
		return postModel;
	}

}
