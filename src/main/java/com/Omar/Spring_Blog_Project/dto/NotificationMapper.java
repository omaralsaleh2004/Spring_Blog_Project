package com.Omar.Spring_Blog_Project.dto;


import com.Omar.Spring_Blog_Project.model.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public NotificationResponse toDto (Notification notification) {
        NotificationResponse notificationResponse = new NotificationResponse();
        if(notification.getPost() != null) {
            notificationResponse.setPostId(notification.getPost().getId());
        }
        else {
            notificationResponse.setPostId(null);
        }
        notificationResponse.setRead(notificationResponse.isRead());
        notificationResponse.setSenderProfileImage("/profile/"+notification.getSender().getId()+"/image");
        notificationResponse.setSenderUsername(notification.getSender().getFirstName() +" "+notification.getSender().getLastName());
        notificationResponse.setType(notification.getType());
        notificationResponse.setCreatedAt(notification.getCreatedAt());
        notificationResponse.setSenderId(notification.getSender().getId());
        notificationResponse.setId(notification.getId());

        return notificationResponse;
    }
}
