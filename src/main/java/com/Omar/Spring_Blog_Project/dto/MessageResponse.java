package com.Omar.Spring_Blog_Project.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageResponse {
    private Integer id;
    private Integer senderId;
    private Integer receiverId;
    private String content;
    private String senderName;
    private String senderAvatar;
    private boolean isRead;
    private LocalDateTime createdAt;
}
