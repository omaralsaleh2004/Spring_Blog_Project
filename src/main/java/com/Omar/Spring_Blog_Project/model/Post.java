package com.Omar.Spring_Blog_Project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;
    private LocalDateTime createdAt;
    private String imageName;
    private String imageType;
    private byte[] imageData;
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "post" , fetch = FetchType.LAZY , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Comment> comments;
    @OneToMany(mappedBy = "post" , fetch = FetchType.LAZY , cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Like> likes;

}
