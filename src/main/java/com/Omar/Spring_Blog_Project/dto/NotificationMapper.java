package com.Omar.Spring_Blog_Project.dto;


import com.Omar.Spring_Blog_Project.model.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public NotificationResponse toDto (Notification notification) {
        NotificationResponse notificationResponse = new NotificationResponse();
        notificationResponse.setRead(notificationResponse.isRead());
        notificationResponse.setType(notification.getType());
        notificationResponse.setCreatedAt(notification.getCreatedAt());

        return notificationResponse;
    }
}
