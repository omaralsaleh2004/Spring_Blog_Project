package com.Omar.Spring_Blog_Project.repo;

import com.Omar.Spring_Blog_Project.model.Follow;
import com.Omar.Spring_Blog_Project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FollowRepo extends JpaRepository<Follow,Integer> {

    boolean existsByFollowerAndFollowing(User user, User following);

    Optional<Follow> findByFollowerAndFollowing(User user, User following);
}
