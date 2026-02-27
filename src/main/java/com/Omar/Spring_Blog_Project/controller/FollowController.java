package com.Omar.Spring_Blog_Project.controller;

import com.Omar.Spring_Blog_Project.dto.ApiResponse;
import com.Omar.Spring_Blog_Project.dto.FollowResponse;
import com.Omar.Spring_Blog_Project.dto.FollowStats;
import com.Omar.Spring_Blog_Project.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<ApiResponse<String>> unFollowUser(@PathVariable int userId) {
        followService.unFollowUser(userId);
        ApiResponse<String> response = new ApiResponse<>(
                "UnFollowed successfully",
                null
        );
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/{userId}/followers")
    public ResponseEntity<List<FollowResponse>> getFollowers (@PathVariable int userId , @RequestParam(defaultValue = "0") int page) {
        List<FollowResponse> followResponse = followService.getFollowers(userId , page);
        return ResponseEntity.ok().body(followResponse);
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<List<FollowResponse>> getFollowing (@PathVariable int userId , @RequestParam(defaultValue = "0") int page) {
        List<FollowResponse> followResponse = followService.getFollowing(userId , page);
        return ResponseEntity.ok().body(followResponse);
    }
    @GetMapping("/{userId}/follow-stats")
    public ResponseEntity<FollowStats> getFollowsStats (@PathVariable int userId) {
        FollowStats followStats = followService.getFollowStats(userId);
        return ResponseEntity.ok().body(followStats);
    }
}
