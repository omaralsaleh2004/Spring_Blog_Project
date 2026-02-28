package com.Omar.Spring_Blog_Project.dto;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedPostsResponse {
    private int pageNumber;
    private int pageSize;
    private int totalPages;
    private long totalItems;
    private List<PostResponse> posts;
}
