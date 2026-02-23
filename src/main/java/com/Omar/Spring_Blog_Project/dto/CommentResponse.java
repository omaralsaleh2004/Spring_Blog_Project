package com.Omar.Spring_Blog_Project.dto;

public record CommentResponse(
        int id,
        String content,
        int postId,
        String username
) {
}
