package com.Omar.Spring_Blog_Project.service;


import com.Omar.Spring_Blog_Project.dto.PostMapper;
import com.Omar.Spring_Blog_Project.dto.PostRequest;
import com.Omar.Spring_Blog_Project.dto.PostResponse;
import com.Omar.Spring_Blog_Project.exception.HandelAllException;
import com.Omar.Spring_Blog_Project.exception.InvalidDataException;
import com.Omar.Spring_Blog_Project.exception.NotFoundException;
import com.Omar.Spring_Blog_Project.exception.UnauthorizedException;
import com.Omar.Spring_Blog_Project.model.Post;
import com.Omar.Spring_Blog_Project.model.User;
import com.Omar.Spring_Blog_Project.repo.CommentRepo;
import com.Omar.Spring_Blog_Project.repo.LikeRepo;
import com.Omar.Spring_Blog_Project.repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostService {
    @Autowired
    AuthService authService;
    @Autowired
    PostRepo postRepo;
    @Autowired
    PostMapper postMapper;
    @Autowired
    CommentRepo commentRepo;
    @Autowired
    LikeRepo likeRepo;
    public PostResponse createPost(PostRequest postRequest, MultipartFile img) {
        User user = authService.getCurrentUser();

        if (user == null) {
            throw new UnauthorizedException("UnAuthorized");
        }


        if (postRequest.getTitle() == null || postRequest.getTitle().trim().isEmpty()
                || postRequest.getDescription() == null || postRequest.getDescription().trim().isEmpty()) {

            throw new InvalidDataException("Title and description are mandatory");
        }
        Post p = new Post();
        p.setTitle(postRequest.getTitle());
        p.setDescription(postRequest.getDescription());
        p.setCreatedAt(LocalDateTime.now());
        p.setUser(user);
        p.setImageName(img.getOriginalFilename());
        p.setImageType(img.getContentType());

        try {
            p.setImageData(img.getBytes());
        } catch (IOException e) {
            throw new HandelAllException(e.getMessage());
        }


        Post savedPost =  postRepo.save(p);

        int numOfLikes = likeRepo.countByPostId(savedPost.getId());
        int numOfComment = commentRepo.countByPostId(savedPost.getId());

        return postMapper.toDto(savedPost , numOfLikes , numOfComment);
    }

    public PostResponse editPost(PostRequest postRequest, MultipartFile img , int postId) {
        User user = authService.getCurrentUser();

        if (user == null) {
            throw new UnauthorizedException("UnAuthorized");
        }

        Post post = postRepo.findById(postId).orElseThrow(() -> new NotFoundException("Post Not Found"));

        if(!user.getId().equals(post.getUser().getId())) {
            throw new UnauthorizedException("You can only edit your own post");
        }

        if (postRequest.getTitle() != null && !postRequest.getTitle().trim().isEmpty()) {
            post.setTitle(postRequest.getTitle());
        }

        if (postRequest.getDescription() != null && !postRequest.getDescription().trim().isEmpty()) {
            post.setDescription(postRequest.getDescription());
        }

        if (img != null && !img.isEmpty()) {
            post.setImageName(img.getOriginalFilename());
            post.setImageType(img.getContentType());
            try {
                post.setImageData(img.getBytes());
            } catch (IOException e) {
                throw new HandelAllException(e.getMessage());
            }
        }

        Post updatedPost = postRepo.save(post);

        int numOfLikes = likeRepo.countByPostId(updatedPost.getId());
        int numOfComment = commentRepo.countByPostId(updatedPost.getId());

        return postMapper.toDto(updatedPost , numOfLikes , numOfComment);
    }

    @Transactional
    public void deletePost(int postId) {
        User user = authService.getCurrentUser();

        if (user == null) {
            throw new UnauthorizedException("UnAuthorized");
        }
        Post post = postRepo.findById(postId).orElseThrow(() -> new NotFoundException("Post Not Found"));

        if(!user.getId().equals(post.getUser().getId())) {
            throw new UnauthorizedException("You can only delete your own post");
        }

        postRepo.delete(post);
    }

    public PostResponse getPostById(int postId) {
        User user = authService.getCurrentUser();

        if (user == null) {
            throw new UnauthorizedException("UnAuthorized");
        }
        Post post = postRepo.findById(postId).orElseThrow(() -> new NotFoundException("Post Not Found"));

        int numOfLikes = likeRepo.countByPostId(post.getId());
        int numOfComment = commentRepo.countByPostId(post.getId());

        return postMapper.toDto(post , numOfLikes , numOfComment);
    }

    public List<PostResponse> getAllPost() {
        User user = authService.getCurrentUser();

        if (user == null) {
            throw new UnauthorizedException("UnAuthorized");
        }

        List<Post> posts = postRepo.findAll();

        if(posts.isEmpty()) {
            throw new NotFoundException("No posts available");
        }

        return posts.stream().map(post ->  {
            int numOfLikes = likeRepo.countByPostId(post.getId());
            int numOfComment = commentRepo.countByPostId(post.getId());

            return postMapper.toDto(post , numOfLikes , numOfComment);
        }).toList();

    }
}
