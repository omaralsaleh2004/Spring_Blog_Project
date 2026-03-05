package com.Omar.Spring_Blog_Project.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedNotificationResponse {
    private List<NotificationResponse> notifications;
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long totalItems;
}
