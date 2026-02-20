package com.Omar.Spring_Blog_Project.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostResponse {
    private Integer id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private Integer authorId;
    private String authorName;
    private Integer likeCount;
    private Integer commentCount;
}
