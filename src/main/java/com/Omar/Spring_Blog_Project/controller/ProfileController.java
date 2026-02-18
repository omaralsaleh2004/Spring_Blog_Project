package com.Omar.Spring_Blog_Project.controller;


import com.Omar.Spring_Blog_Project.dto.ApiResponse;
import com.Omar.Spring_Blog_Project.dto.ProfileResponse;
import com.Omar.Spring_Blog_Project.model.Profile;
import com.Omar.Spring_Blog_Project.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    ProfileService profileService;

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ProfileResponse>> createProfile(@RequestPart Profile p , @RequestPart MultipartFile img) {
        ProfileResponse profileResponse = profileService.createProfile(p , img);
        ApiResponse<ProfileResponse> response = new ApiResponse<>(
                "Profile Created Successfully",
                profileResponse
        );
        return ResponseEntity.ok(response);
    }
    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> getMyProfile () {
        ProfileResponse profileResponse = profileService.getMyProfile();
        return ResponseEntity.ok().body(profileResponse);
    }
}
