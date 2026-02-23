package com.Omar.Spring_Blog_Project.dto;

import java.time.LocalDateTime;

public record CommentResponse(
        int id,
        String content,
        int postId,
        String username,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}
