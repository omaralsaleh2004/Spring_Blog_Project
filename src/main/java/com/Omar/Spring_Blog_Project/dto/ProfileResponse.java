package com.Omar.Spring_Blog_Project.dto;


import com.Omar.Spring_Blog_Project.model.User;
import lombok.Data;

@Data
public class ProfileResponse {
    private Integer id;
    private Integer userId;
    private String username;
    private String jobTitle;
    private String location;
    private String profileImgUrl;
}