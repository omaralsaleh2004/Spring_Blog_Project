package com.Omar.Spring_Blog_Project.repo;

import com.Omar.Spring_Blog_Project.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {
    @Query("Select p from Post p Order by Coalesce (p.updatedAt , p.createdAt) desc")
    Page<Post> findAllOrdered(Pageable pageable);
}
