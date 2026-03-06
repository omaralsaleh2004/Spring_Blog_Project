package com.Omar.Spring_Blog_Project.service;

import com.Omar.Spring_Blog_Project.dto.NotificationMapper;
import com.Omar.Spring_Blog_Project.dto.NotificationResponse;
import com.Omar.Spring_Blog_Project.dto.NotificationType;
import com.Omar.Spring_Blog_Project.dto.PaginatedNotificationResponse;
import com.Omar.Spring_Blog_Project.exception.UnauthorizedException;
import com.Omar.Spring_Blog_Project.model.Notification;
import com.Omar.Spring_Blog_Project.model.Post;
import com.Omar.Spring_Blog_Project.model.User;
import com.Omar.Spring_Blog_Project.repo.NotificationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final AuthService authService;
    private final NotificationRepo notificationRepo;
    private final NotificationMapper notificationMapper;

    public PaginatedNotificationResponse getAllNotifications(int page) {
        User user = authService.getCurrentUser();

        if(user == null) {
            throw new UnauthorizedException("UnAuthorized");
        }

        Pageable pageable = PageRequest.of(page , 10);
        Page<Notification> notifications = notificationRepo.findByReceiverOrderByCreatedAtDesc(user , pageable);

        List<NotificationResponse> notificationResponses = notifications
                .stream()
                .map(notificationMapper::toDto)
                .toList();

        PaginatedNotificationResponse paginatedResponse = new PaginatedNotificationResponse();
        paginatedResponse.setNotifications(notificationResponses);
        paginatedResponse.setPageSize(notifications.getSize());
        paginatedResponse.setPageNumber(notifications.getNumber());
        paginatedResponse.setTotalItems(notifications.getTotalElements());
        paginatedResponse.setTotalPages(notifications.getTotalPages());

        return paginatedResponse;
    }

    public long getUnreadCount() {
        User user = authService.getCurrentUser();

        if (user == null) {
            throw new UnauthorizedException("Unauthorized");
        }

        return notificationRepo.countByReceiverAndIsReadFalse(user);
    }

    public PaginatedNotificationResponse getUnreadNotifications(int page) {
        User user = authService.getCurrentUser();

        if (user == null) {
            throw new UnauthorizedException("Unauthorized");
        }

        Pageable pageable = PageRequest.of(page , 10);

        Page<Notification> notifications = notificationRepo.findByReceiverAndIsReadFalseOrderByCreatedAtDesc(user , pageable);

        List<NotificationResponse> notificationResponses = notifications
                .stream()
                .map(notificationMapper::toDto)
                .toList();

        PaginatedNotificationResponse paginatedResponse = new PaginatedNotificationResponse();
        paginatedResponse.setNotifications(notificationResponses);
        paginatedResponse.setPageSize(notifications.getSize());
        paginatedResponse.setPageNumber(notifications.getNumber());
        paginatedResponse.setTotalItems(notifications.getTotalElements());
        paginatedResponse.setTotalPages(notifications.getTotalPages());

        return paginatedResponse;
    }

    @Async
    public void createNotification (User sender , User receiver , NotificationType type , Post post) {

        if(sender.getId().equals(receiver.getId())) {
            return;
        }

        Notification notification = new Notification();
        notification.setSender(sender);
        notification.setReceiver(receiver);
        notification.setRead(false);
        notification.setType(type);
        notification.setPost(post);
        notification.setCreatedAt(LocalDateTime.now());

        notificationRepo.save(notification);
    }
}
