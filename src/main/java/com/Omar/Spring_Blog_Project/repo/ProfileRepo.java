package com.Omar.Spring_Blog_Project.repo;

import com.Omar.Spring_Blog_Project.model.Profile;
import com.Omar.Spring_Blog_Project.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepo extends JpaRepository<Profile,Integer> {
    Profile findByUser(User user);

    @Query("""
            Select p from Profile p
            Join p.user u
            Where Lower(u.firstName) Like Lower (Concat('%' , :keyword , '%'))
            Or Lower(u.lastName) Like Lower (Concat('%' , :keyword , '%'))
            Or Lower(p.jobTitle) Like Lower (Concat('%' , :keyword , '%'))
            Or Lower(p.location) Like Lower (Concat('%' , :keyword , '%'))
            """)
    Page<Profile> searchProfile(@Param("keyword") String keyword, Pageable pageable);
}
