package com.Omar.Spring_Blog_Project.controller;

import com.Omar.Spring_Blog_Project.dto.PaginatedNotificationResponse;
import com.Omar.Spring_Blog_Project.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;


    @GetMapping("")
    public ResponseEntity<PaginatedNotificationResponse> getNotifications (@RequestParam(defaultValue = "0") int page) {
        PaginatedNotificationResponse notificationResponse = notificationService.getAllNotifications(page);
        return ResponseEntity.ok().body(notificationResponse);
    }

    @GetMapping("/unread")
    public ResponseEntity<PaginatedNotificationResponse> getUnreadNotifications (@RequestParam(defaultValue = "0") int page) {
        PaginatedNotificationResponse notificationResponse = notificationService.getUnreadNotifications(page);
        return ResponseEntity.ok().body(notificationResponse);
    }

    @GetMapping("unread-count")
    public Map<String,Long> getUnreadNotificationsCount (@RequestParam(defaultValue = "0") int page) {
       long count = notificationService.getUnreadCount();
       return Map.of("unreadCount" , count);
    }

}
