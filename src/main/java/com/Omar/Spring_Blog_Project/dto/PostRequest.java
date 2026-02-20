package com.Omar.Spring_Blog_Project.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostRequest {
    private String title;
    private String description;

}
