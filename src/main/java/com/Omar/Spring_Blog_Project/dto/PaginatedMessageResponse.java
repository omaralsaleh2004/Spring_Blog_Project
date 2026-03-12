package com.Omar.Spring_Blog_Project.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedMessageResponse {
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long totalItems;
    // receiver Info
    private List<MessageResponse> messages;
    private String receiverName;
    private String receiverAvatar;
}
