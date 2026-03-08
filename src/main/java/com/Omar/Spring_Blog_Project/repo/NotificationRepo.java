package com.Omar.Spring_Blog_Project.repo;

import com.Omar.Spring_Blog_Project.model.Notification;
import com.Omar.Spring_Blog_Project.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepo extends JpaRepository<Notification,Integer> {
     Page<Notification> findByReceiverOrderByCreatedAtDesc(User user, Pageable pageable);

    long countByReceiverAndIsReadFalse(User user);

    Page<Notification> findByReceiverAndIsReadFalseOrderByCreatedAtDesc(User user, Pageable pageable);

    @Modifying
    @Query("Update Notification n Set n.isRead = true Where n.receiver = :user")
    void markAllAsRead(@Param("user") User user);
}
