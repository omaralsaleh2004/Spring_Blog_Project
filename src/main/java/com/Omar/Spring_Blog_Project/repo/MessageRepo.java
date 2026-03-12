package com.Omar.Spring_Blog_Project.repo;

import com.Omar.Spring_Blog_Project.model.Message;
import com.Omar.Spring_Blog_Project.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MessageRepo extends JpaRepository<Message , Integer> {

    @Query("""
            Select m from Message m
            Where (m.sender = :user And m.receiver = :otherUser)
            Or (m.sender = :otherUser And m.receiver = :user)
            Order By m.createdAt Asc
            """)
    Page<Message> findChatBetweenUsers(@Param("user") User user,@Param("otherUser") User otherUser, Pageable pageable);
}
