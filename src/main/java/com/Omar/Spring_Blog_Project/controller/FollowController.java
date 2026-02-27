package com.Omar.Spring_Blog_Project.controller;

import com.Omar.Spring_Blog_Project.dto.ApiResponse;
import com.Omar.Spring_Blog_Project.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("follows")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;

    @PostMapping("/{userId}/follow")
    public ResponseEntity<ApiResponse<String>> followUser(@PathVariable int userId) {
        followService.followUser(userId);
        ApiResponse<String> response = new ApiResponse<>(
                "Followed successfully",
                null
        );
        return ResponseEntity.ok().body(response);
    }
    @PostMapping("/{userId}/unfollow")
    public ResponseEntity<ApiResponse<String>> ubFollowUser(@PathVariable int userId) {
        followService.unFollowUser(userId);
        ApiResponse<String> response = new ApiResponse<>(
                "UnFollowed successfully",
                null
        );
        return ResponseEntity.ok().body(response);
    }
}
