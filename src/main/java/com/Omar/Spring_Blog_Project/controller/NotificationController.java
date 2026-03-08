package com.Omar.Spring_Blog_Project.controller;

import com.Omar.Spring_Blog_Project.dto.ApiResponse;
import com.Omar.Spring_Blog_Project.dto.PaginatedNotificationResponse;
import com.Omar.Spring_Blog_Project.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/unread-count")
    public Map<String,Long> getUnreadNotificationsCount (@RequestParam(defaultValue = "0") int page) {
       long count = notificationService.getUnreadCount();
       return Map.of("unreadCount" , count);
    }

    @PatchMapping("/{notificationId}/read")
    public ResponseEntity<ApiResponse<String>> readNotification (@PathVariable int notificationId) {
        notificationService.readNotification(notificationId);
        ApiResponse<String> apiResponse = new ApiResponse<>(
                "Notifications marked as read",
                null
        );
        return ResponseEntity.ok().body(apiResponse);
    }

    @PatchMapping("/read-all")
    public ResponseEntity<ApiResponse<String>> readAllNotification () {
        notificationService.readAllNotification();
        ApiResponse<String> apiResponse = new ApiResponse<>(
                "All Notifications marked as read",
                null
        );
        return ResponseEntity.ok().body(apiResponse);
    }


}
