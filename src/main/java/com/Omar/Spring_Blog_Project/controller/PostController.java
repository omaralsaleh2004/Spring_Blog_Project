package com.Omar.Spring_Blog_Project.controller;

import com.Omar.Spring_Blog_Project.dto.ApiResponse;
import com.Omar.Spring_Blog_Project.dto.PostRequest;
import com.Omar.Spring_Blog_Project.dto.PostResponse;
import com.Omar.Spring_Blog_Project.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<PostResponse>> createPost(@RequestPart PostRequest postRequest , MultipartFile img) {
          PostResponse postResponse = postService.createPost(postRequest , img);
          ApiResponse<PostResponse> response = new ApiResponse<>(
                  "Post Created Successfully",
                  postResponse
          );
          return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
