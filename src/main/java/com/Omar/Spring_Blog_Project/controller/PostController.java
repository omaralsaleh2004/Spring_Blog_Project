package com.Omar.Spring_Blog_Project.controller;

import com.Omar.Spring_Blog_Project.dto.*;
import com.Omar.Spring_Blog_Project.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {


    private final PostService postService;

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
    @PostMapping("/{postId}/like")
    public ResponseEntity<ApiResponse<LikeResponse>> likePost(@PathVariable int postId) {
        LikeResponse likeResponse = postService.likePost(postId);
        ApiResponse<LikeResponse> response = new ApiResponse<>(
                "Post Liked Successfully",
                likeResponse
        );
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{postId}/like")
    public ResponseEntity<ApiResponse<LikeResponse>> unlikePost(@PathVariable int postId) {
        LikeResponse likeResponse = postService.unlikePost(postId);
        ApiResponse<LikeResponse> response = new ApiResponse<>(
                "Post UnLiked Successfully",
                likeResponse
        );
        return ResponseEntity.ok().body(response);
    }
    @PostMapping("/{postId}/comment")
    public ResponseEntity<ApiResponse<CommentResponse>> commentPost(@RequestBody CommentRequest commentRequest, @PathVariable int postId) {
        CommentResponse commentResponse = postService.addComment(commentRequest , postId);
        ApiResponse<CommentResponse> response = new ApiResponse<>(
          "Comment added Successfully",
                commentResponse
        );
        return ResponseEntity.ok().body(response);
    }
    @DeleteMapping("/{postId}/comment/{commentId}")
    public ResponseEntity<ApiResponse<String>> commentPost(@PathVariable int postId , @PathVariable int commentId) {
        postService.deleteComment(postId,commentId);
        ApiResponse<String> response = new ApiResponse<>(
                "Comment deleted Successfully",
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
