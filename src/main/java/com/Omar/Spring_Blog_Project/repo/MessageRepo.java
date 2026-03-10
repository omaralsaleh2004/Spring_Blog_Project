package com.Omar.Spring_Blog_Project.repo;

import com.Omar.Spring_Blog_Project.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepo extends JpaRepository<Message , Integer> {

}
