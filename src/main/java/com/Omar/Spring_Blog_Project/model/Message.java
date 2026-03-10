package com.Omar.Spring_Blog_Project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String content;
    @ManyToOne
    private User sender;
    @ManyToOne
    private User receiver;
    private boolean isRead;
    private LocalDateTime createdAt;
}
