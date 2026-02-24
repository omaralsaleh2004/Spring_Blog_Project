package com.Omar.Spring_Blog_Project.repo;

import com.Omar.Spring_Blog_Project.model.Comment;
import com.Omar.Spring_Blog_Project.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Integer> {
    @Query("select count(c) from Comment c where c.post.Id = :postId")
    int countByPostId(@Param("postId") int postId);
    @Query("select c from Comment c where c.post = :post order by coalesce (c.updatedAt , c.createdAt) desc")
    List<Comment> findLatestCommentsByPostId(@Param("post") Post post);
}
