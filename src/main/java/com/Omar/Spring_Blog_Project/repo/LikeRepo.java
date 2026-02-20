package com.Omar.Spring_Blog_Project.repo;

import com.Omar.Spring_Blog_Project.model.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepo extends JpaRepository<Like , Integer> {
    @Query("select count(l) from Like l where l.post.Id = :postId")
    int countByPostId(@Param("postId") int postId);
}
