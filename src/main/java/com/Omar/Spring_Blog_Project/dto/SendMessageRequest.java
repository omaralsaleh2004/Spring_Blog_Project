package com.Omar.Spring_Blog_Project.dto;

public record SendMessageRequest(
        Integer receiverId,
        String content
) {
}
