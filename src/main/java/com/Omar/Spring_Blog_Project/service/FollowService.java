package com.Omar.Spring_Blog_Project.service;

import com.Omar.Spring_Blog_Project.dto.FollowMapper;
import com.Omar.Spring_Blog_Project.dto.FollowResponse;
import com.Omar.Spring_Blog_Project.dto.FollowStats;
import com.Omar.Spring_Blog_Project.exception.BadRequest;
import com.Omar.Spring_Blog_Project.exception.NotFoundException;
import com.Omar.Spring_Blog_Project.exception.UnauthorizedException;
import com.Omar.Spring_Blog_Project.model.Follow;
import com.Omar.Spring_Blog_Project.model.User;
import com.Omar.Spring_Blog_Project.repo.FollowRepo;
import com.Omar.Spring_Blog_Project.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final AuthService authService;
    private final FollowRepo followRepo;
    private final UserRepo userRepo;
    private final FollowMapper followMapper;

    public void followUser(int userId) {
        User user = authService.getCurrentUser();

        if(user == null) {
            throw new UnauthorizedException("UnAuthorized");
        }

        User following = userRepo.findById(userId).orElseThrow(() -> new NotFoundException("User Not Found"));


        if(user.getId().equals(userId)) {
            throw new BadRequest("User cannot follow themselves");
        }

        boolean exists = followRepo.existsByFollowerAndFollowing(user , following);

        if(exists) {
            throw new BadRequest("You already follow this user");
        }

        Follow follow = new Follow();
        follow.setFollowing(following);
        follow.setFollower(user);

        followRepo.save(follow);
    }

    public void unFollowUser(int userId) {

        User user = authService.getCurrentUser();

        if(user == null) {
            throw new UnauthorizedException("UnAuthorized");
        }

        User following = userRepo.findById(userId).orElseThrow(() -> new NotFoundException("User Not Found"));


        if(user.getId().equals(userId)) {
            throw new BadRequest("User cannot Unfollow yourself");
        }

        Follow follow = followRepo.findByFollowerAndFollowing(user , following)
                .orElseThrow(() -> new NotFoundException("You are not following this user"));

        followRepo.delete(follow);
    }

    public List<FollowResponse> getFollowers(int userId , int page) {
        User user = authService.getCurrentUser();

        if(user == null) {
            throw new UnauthorizedException("UnAuthorized");
        }

        User currentUser = userRepo.findById(userId).orElseThrow(() -> new NotFoundException("User Not Found"));

        int fixedSize = 20;
        Pageable pageable = PageRequest.of(page , fixedSize);
        Page<Follow> followers = followRepo.findByFollowing(currentUser , pageable);

        return followers
                .stream()
                .map(followMapper::toDtoFollowers).toList();
    }

    public List<FollowResponse> getFollowing(int userId , int page) {
        User user = authService.getCurrentUser();

        if(user == null) {
            throw new UnauthorizedException("UnAuthorized");
        }

        User currentUser = userRepo.findById(userId).orElseThrow(() -> new NotFoundException("User Not Found"));
        int fixedSize = 20;
        Pageable pageable = PageRequest.of(page , fixedSize);
        Page<Follow> following = followRepo.findByFollower(currentUser , pageable);

        return following
                .stream()
                .map(followMapper::toDtoFollowing)
                .toList();
    }

    public FollowStats getFollowStats(int userId) {
        User user = authService.getCurrentUser();

        if(user == null) {
            throw new UnauthorizedException("UnAuthorized");
        }

        User currentUser = userRepo.findById(userId).orElseThrow(() -> new NotFoundException("User Not Found"));

        int followersCount = followRepo.countByFollowing(currentUser);
        int followingCount = followRepo.countByFollower(currentUser);

        return new FollowStats(followersCount , followingCount);
    }
}
