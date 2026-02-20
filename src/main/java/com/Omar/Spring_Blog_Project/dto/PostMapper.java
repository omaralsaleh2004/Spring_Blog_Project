package com.Omar.Spring_Blog_Project.dto;

import com.Omar.Spring_Blog_Project.model.Post;
import org.springframework.stereotype.Component;

@Component
public class PostMapper {
    public PostResponse toDto(Post p , int likes , int comment) {
        PostResponse postResponse = new PostResponse();
        postResponse.setId(p.getId());
        postResponse.setDescription(p.getDescription());
        postResponse.setTitle(p.getTitle());
        postResponse.setAuthorId(p.getUser().getId());
        postResponse.setAuthorName(p.getUser().getFirstName() + " " + p.getUser().getLastName());
        postResponse.setCommentCount(comment);
        postResponse.setLikeCount(likes);
        postResponse.setCreatedAt(p.getCreatedAt());

        return postResponse;
    }
}
