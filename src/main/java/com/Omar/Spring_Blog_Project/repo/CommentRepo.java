package com.Omar.Spring_Blog_Project.repo;

import com.Omar.Spring_Blog_Project.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Integer> {
    @Query("select count(c) from Comment c where c.post.Id = :postId")
    int countByPostId(@Param("postId") int postId);
}
