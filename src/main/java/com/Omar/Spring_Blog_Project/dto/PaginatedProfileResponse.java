package com.Omar.Spring_Blog_Project.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedProfileResponse {
    private List<ProfileResponse> profiles;
    private int pageNumber;
    private int pageSize;
    private long totalItems;
    private int totalPages;
}
