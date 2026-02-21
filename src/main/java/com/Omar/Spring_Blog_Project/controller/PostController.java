package com.Omar.Spring_Blog_Project.controller;

import com.Omar.Spring_Blog_Project.dto.ApiResponse;
import com.Omar.Spring_Blog_Project.dto.PostRequest;
import com.Omar.Spring_Blog_Project.dto.PostResponse;
import com.Omar.Spring_Blog_Project.model.Post;
import com.Omar.Spring_Blog_Project.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    @PutMapping("/{postId}")
    public ResponseEntity<ApiResponse<PostResponse>>editPost (@RequestPart PostRequest postRequest , MultipartFile img , @PathVariable int postId) {
        PostResponse postResponse = postService.editPost(postRequest , img , postId);
        ApiResponse<PostResponse> response = new ApiResponse<>(
                "Post Edited Successfully",
                postResponse
        );
        return ResponseEntity.ok().body(response);
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<String>> deletePost(@PathVariable int postId) {
        postService.deletePost(postId);
        ApiResponse<String> response = new ApiResponse<>(
                "Post Deleted Successfully",
                null
        );
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostById (@PathVariable int postId) {
        PostResponse postResponse = postService.getPostById(postId);
        return ResponseEntity.ok().body(postResponse);
    }
    @GetMapping("")
    public ResponseEntity<List<PostResponse>> getAllPosts () {
        List<PostResponse> postResponses = postService.getAllPost();
        return ResponseEntity.ok().body(postResponses);
    }
}
