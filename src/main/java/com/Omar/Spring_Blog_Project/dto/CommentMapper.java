package com.Omar.Spring_Blog_Project.dto;

import com.Omar.Spring_Blog_Project.model.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {
    public CommentResponse toDto (Comment comment) {
        CommentResponse commentResponse = new CommentResponse(
                comment.getId(),
                comment.getCommentDescription(),
                comment.getPost().getId(),
                comment.getUser().getFirstName() + " " + comment.getUser().getLastName(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );

        return commentResponse;
    }
}
