package com.Omar.Spring_Blog_Project.repo;

import com.Omar.Spring_Blog_Project.model.Profile;
import com.Omar.Spring_Blog_Project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepo extends JpaRepository<Profile,Integer> {
    Profile findByUser(User user);
}
