package com.Omar.Spring_Blog_Project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;
    private String jobTitle;
    private String location;
    private String imageName;
    private String imageType;
    @Lob
    private byte[] imageData;
    @OneToOne
    User user;
}
