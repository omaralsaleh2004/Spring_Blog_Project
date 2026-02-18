package com.Omar.Spring_Blog_Project.service;

import com.Omar.Spring_Blog_Project.dto.ProfileMapper;
import com.Omar.Spring_Blog_Project.dto.ProfileRequest;
import com.Omar.Spring_Blog_Project.dto.ProfileResponse;
import com.Omar.Spring_Blog_Project.exception.HandelAllException;
import com.Omar.Spring_Blog_Project.exception.InvalidDataException;
import com.Omar.Spring_Blog_Project.exception.NotFoundException;
import com.Omar.Spring_Blog_Project.exception.UnauthorizedException;
import com.Omar.Spring_Blog_Project.model.Profile;
import com.Omar.Spring_Blog_Project.model.User;
import com.Omar.Spring_Blog_Project.repo.ProfileRepo;
import com.Omar.Spring_Blog_Project.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProfileService {

    @Autowired
    UserRepo userRepo;
    @Autowired
    AuthService authService;
    @Autowired
    ProfileRepo profileRepo;
    @Autowired
    ProfileMapper profileMapper;

    @Transactional
    public ProfileResponse createProfile(Profile p, MultipartFile img) {
        User user = authService.getCurrentUser();
        if (user == null) {
            throw new UnauthorizedException("UnAuthorized");
        }
        if (user.getProfile() != null) {
            throw new InvalidDataException("Profile already exists");
        }

        // Set profile fields
        p.setImageName(img.getOriginalFilename());
        p.setImageType(img.getContentType());
        try {
            p.setImageData(img.getBytes());
        } catch (IOException e) {
            throw new HandelAllException(e.getMessage());
        }

        p.setUser(user);
        user.setProfile(p);

        profileRepo.save(p);
        userRepo.save(user);

        return profileMapper.toDto(user.getProfile());
    }


    public ProfileResponse getMyProfile() {
        User user = authService.getCurrentUser();
        if (user == null) {
            throw new UnauthorizedException("UnAuthorized");
        }
        if (user.getProfile() == null) {
            throw new NotFoundException("There is no profile for you, please create one");
        }

       Profile profile = profileRepo.findByUser(user);

        if(profile == null) {
            throw new NotFoundException("Profile Not Found");
        }

        return profileMapper.toDto(profile);
    }

    public List<ProfileResponse> getAllProfiles() {
        User user = authService.getCurrentUser();
        if (user == null) {
            throw new UnauthorizedException("UnAuthorized");
        }
        List<Profile> profiles = profileRepo.findAll();
        if(profiles.isEmpty()) {
            throw new NotFoundException("No Profiles Found");
        }
        return profiles.stream().map(profile -> profileMapper.toDto(profile)).toList();
    }

    @Transactional
    public void deleteProfile() {
        User user = authService.getCurrentUser();
        if (user == null) {
            throw new UnauthorizedException("UnAuthorized");
        }
        Profile profile = profileRepo.findByUser(user);

        if(profile == null) {
            throw new NotFoundException("Profile Not Found");
        }
        user.setProfile(null);
        profile.setUser(null);

        userRepo.save(user);

        profileRepo.delete(profile);
    }

    public ProfileResponse editProfile(ProfileRequest p , MultipartFile img) {
        User user = authService.getCurrentUser();
        if (user == null) {
            throw new UnauthorizedException("UnAuthorized");
        }
        Profile profile = profileRepo.findByUser(user);

        if(profile == null) {
            throw new NotFoundException("Profile Not Found");
        }
        if(p.getLocation() != null) {
            profile.setLocation(p.getLocation());
        }
        if(p.getJobTitle() != null) {
            profile.setJobTitle(p.getJobTitle());
        }
        if (img != null && !img.isEmpty()) {
            profile.setImageName(img.getOriginalFilename());
            profile.setImageType(img.getContentType());
            try {
                profile.setImageData(img.getBytes());
            } catch (IOException e) {
                throw new HandelAllException(e.getMessage());
            }
        }

        profileRepo.save(profile);
        return profileMapper.toDto(profile);
    }
}
