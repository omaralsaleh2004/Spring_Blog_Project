package com.Omar.Spring_Blog_Project.controller;


import com.Omar.Spring_Blog_Project.dto.ApiResponse;
import com.Omar.Spring_Blog_Project.dto.PaginatedProfileResponse;
import com.Omar.Spring_Blog_Project.dto.ProfileRequest;
import com.Omar.Spring_Blog_Project.dto.ProfileResponse;
import com.Omar.Spring_Blog_Project.model.Profile;
import com.Omar.Spring_Blog_Project.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    @PostMapping("")
    public ResponseEntity<ApiResponse<ProfileResponse>> createProfile(@RequestPart Profile p , @RequestPart MultipartFile img) {
        ProfileResponse profileResponse = profileService.createProfile(p , img);
        ApiResponse<ProfileResponse> response = new ApiResponse<>(
                "Profile Created Successfully",
                profileResponse
        );
        return ResponseEntity.ok(response);
    }

    @PutMapping("")
    public ResponseEntity<ApiResponse<ProfileResponse>> editProfile(@RequestPart ProfileRequest p , @RequestPart MultipartFile img) {
        ProfileResponse profileResponse = profileService.editProfile(p , img);
        ApiResponse<ProfileResponse> response = new ApiResponse<>(
                "Profile Edited Successfully",
                profileResponse
        );
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("")
    public ResponseEntity<ApiResponse<String>>deleteProfile() {
        profileService.deleteProfile();
        ApiResponse<String> response = new ApiResponse(
                "Profile Deleted Successfully",
                null
        );
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> getMyProfile () {
        ProfileResponse profileResponse = profileService.getMyProfile();
        return ResponseEntity.ok().body(profileResponse);
    }

    @GetMapping("/all")
    public ResponseEntity<PaginatedProfileResponse> getAllProfiles(@RequestParam(defaultValue = "0" ) int page) {
        PaginatedProfileResponse paginatedProfileResponse = profileService.getAllProfiles(page);
        return ResponseEntity.ok().body(paginatedProfileResponse);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ProfileResponse> getProfile (@PathVariable int userId) {
        ProfileResponse profileResponse = profileService.getProfile(userId);
        return ResponseEntity.ok().body(profileResponse);
    }

    @GetMapping("/{userId}/image")
    public ResponseEntity<byte[]> getProfileImage(@PathVariable int userId) {
        Profile p  = profileService.getProfileImage(userId);
        return ResponseEntity.ok().body(p.getImageData());
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProfileResponse>> searchProfiles(@RequestParam String keyword,@RequestParam(defaultValue = "0") int page) {
        List<ProfileResponse> profileResponseList = profileService.searchProfiles(keyword,page);
        return ResponseEntity.ok().body(profileResponseList);
    }

    @DeleteMapping("/me/image")
    public ResponseEntity<ApiResponse<String>> deleteProfileImage() {
        profileService.deleteProfileImage();
        ApiResponse<String> response = new ApiResponse(
          "Profile Image Deleted Successfully",
          null
        );
        return ResponseEntity.ok().body(response);
    }


}
