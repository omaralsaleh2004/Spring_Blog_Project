package com.Omar.Spring_Blog_Project.service;


import com.Omar.Spring_Blog_Project.dto.*;
import com.Omar.Spring_Blog_Project.exception.*;
import com.Omar.Spring_Blog_Project.model.Comment;
import com.Omar.Spring_Blog_Project.model.Like;
import com.Omar.Spring_Blog_Project.model.Post;
import com.Omar.Spring_Blog_Project.model.User;
import com.Omar.Spring_Blog_Project.repo.CommentRepo;
import com.Omar.Spring_Blog_Project.repo.LikeRepo;
import com.Omar.Spring_Blog_Project.repo.PostRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostService {

    private final AuthService authService;
    private final PostRepo postRepo;
    private final PostMapper postMapper;
    private final CommentMapper commentMapper;
    private final CommentRepo commentRepo;
    private final LikeRepo likeRepo;


    @Transactional
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

        if(!img.isEmpty()) {
            try {
                p.setImageName(img.getOriginalFilename());
                p.setImageType(img.getContentType());
                p.setImageData(img.getBytes());
            } catch (IOException e) {
                throw new HandelAllException(e.getMessage());
            }
        }
        p.setCreatedAt(LocalDateTime.now());
        Post savedPost =  postRepo.save(p);

        int numOfLikes = likeRepo.countByPostId(savedPost.getId());
        int numOfComment = commentRepo.countByPostId(savedPost.getId());

        return postMapper.toDto(savedPost , numOfLikes , numOfComment);
    }

    @Transactional
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
        post.setUpdatedAt(LocalDateTime.now());
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

        List<Post> posts = postRepo.findAllOrdered();

        if(posts.isEmpty()) {
            throw new NotFoundException("No posts available");
        }

        return posts.stream().map(post ->  {
            int numOfLikes = likeRepo.countByPostId(post.getId());
            int numOfComment = commentRepo.countByPostId(post.getId());

            return postMapper.toDto(post , numOfLikes , numOfComment);
        }).toList();

    }

    @Transactional
    public LikeResponse likePost(int postId) {
        User user = authService.getCurrentUser();

        if (user == null) {
            throw new UnauthorizedException("UnAuthorized");
        }
        Post post = postRepo.findById(postId).orElseThrow(() -> new NotFoundException("Post Not Found"));

       if(likeRepo.existsByUserAndPost(user , post)) {
           throw new BadRequest("You already liked this post");
       }

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);

        likeRepo.save(like);

        int likeCount = likeRepo.countByPostId(postId);

        return new LikeResponse(post.getId() , likeCount);
    }

    @Transactional
    public LikeResponse unlikePost(int postId) {

        User user = authService.getCurrentUser();

        if (user == null) {
            throw new UnauthorizedException("UnAuthorized");
        }
        Post post = postRepo.findById(postId).orElseThrow(() -> new NotFoundException("Post Not Found"));

        Like like = likeRepo.findByUserAndPost(user , post).orElseThrow(()-> new BadRequest("Cannot unlike this post: you haven't liked it yet"));

        likeRepo.delete(like);

        int likeCount = likeRepo.countByPostId(postId);

        return new LikeResponse(post.getId() , likeCount);
    }

    public CommentResponse addComment(CommentRequest commentRequest, int postId) {
        User user = authService.getCurrentUser();

        if (user == null) {
            throw new UnauthorizedException("UnAuthorized");
        }

        Post post = postRepo.findById(postId).orElseThrow(() -> new NotFoundException("Post Not Found"));

        Comment comment = new Comment();
        comment.setUser(user);
        comment.setPost(post);
        comment.setCommentDescription(commentRequest.content());
        comment.setCreatedAt(LocalDateTime.now());
        Comment savedComment = commentRepo.save(comment);

        return commentMapper.toDto(savedComment);
    }

    public void deleteComment(int postId,int commentId) {
        User user = authService.getCurrentUser();

        if (user == null) {
            throw new UnauthorizedException("UnAuthorized");
        }

        Post post = postRepo.findById(postId).orElseThrow(() -> new NotFoundException("Post Not Found"));

        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new NotFoundException("Comment Not Found"));

        if(!comment.getPost().getId().equals(postId)) {
            throw new BadRequest("Comment does not belong to this post");
        }

        if(!user.getId().equals(comment.getUser().getId())) {
            throw new UnauthorizedException("Not allowed to delete this comment");
        }

        commentRepo.delete(comment);
    }

    public CommentResponse editComment(CommentRequest commentRequest, int postId , int commentId) {
        User user = authService.getCurrentUser();

        if (user == null) {
            throw new UnauthorizedException("UnAuthorized");
        }

        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new NotFoundException("Comment Not Found"));

        if(!comment.getUser().getId().equals(user.getId())) {
            throw new UnauthorizedException("Not allowed to update this comment");
        }
        comment.setCommentDescription(commentRequest.content());
        comment.setUpdatedAt(LocalDateTime.now());
        Comment updated = commentRepo.save(comment);

        return commentMapper.toDto(updated);
    }

    public List<CommentResponse> getAllComments(int postId) {
        User user = authService.getCurrentUser();

        if (user == null) {
            throw new UnauthorizedException("UnAuthorized");
        }

        Post post = postRepo.findById(postId).orElseThrow(() -> new NotFoundException("Post Not Found"));

        List<Comment> comments = commentRepo.findLatestCommentsByPostId(post);

        return comments.stream()
                .map(comment -> commentMapper.toDto(comment)).toList();

    }

    public List<LikeUserResponse> getAllLikes(int postId) {
        User user = authService.getCurrentUser();

        if (user == null) {
            throw new UnauthorizedException("UnAuthorized");
        }

        Post post = postRepo.findById(postId).orElseThrow(() -> new NotFoundException("Post Not Found"));

        List<Like> likes = likeRepo.findByPost(post);

        return likes.stream()
                .map(like -> new LikeUserResponse(like.getUser().getId() , like.getUser().getFirstName() + " "+like.getUser().getLastName()))
                .toList();
    }

    public Post getPostImage(int postId) {
        User user = authService.getCurrentUser();

        if (user == null) {
            throw new UnauthorizedException("UnAuthorized");
        }

        return postRepo.findById(postId).orElseThrow(() -> new NotFoundException("Post Not Found"));
    }
}
