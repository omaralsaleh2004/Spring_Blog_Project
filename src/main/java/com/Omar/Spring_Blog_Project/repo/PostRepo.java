package com.Omar.Spring_Blog_Project.repo;

import com.Omar.Spring_Blog_Project.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {
    @Query("Select p from Post p Order by Coalesce (p.updatedAt , p.createdAt) desc")
    List<Post> findAllOrdered();
}
