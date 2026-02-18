package com.Omar.Spring_Blog_Project.dto;

import com.Omar.Spring_Blog_Project.model.Profile;
import org.springframework.stereotype.Component;


@Component
public class ProfileMapper {

    public ProfileResponse toDto(Profile profile) {
        ProfileResponse dto = new ProfileResponse();
        dto.setId(profile.getId());
        dto.setLocation(profile.getLocation());
        dto.setJobTitle(profile.getJobTitle());
        dto.setImageName(profile.getImageName());  // <-- copy from profile
        dto.setImageType(profile.getImageType());
        dto.setImageData(profile.getImageData());

        return dto;
    }
}
