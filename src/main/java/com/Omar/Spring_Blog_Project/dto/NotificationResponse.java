package com.Omar.Spring_Blog_Project.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationResponse {
    private int id;
    private NotificationType type;
    private boolean isRead;
    private LocalDateTime CreatedAt;
}
