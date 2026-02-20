package com.Omar.Spring_Blog_Project.repo;

import com.Omar.Spring_Blog_Project.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {
}
