package com.Omar.Spring_Blog_Project.dto;

import com.Omar.Spring_Blog_Project.model.Profile;
import org.springframework.stereotype.Component;


@Component
public class ProfileMapper {

    public ProfileResponse toDto(Profile profile) {
        ProfileResponse dto = new ProfileResponse();
        dto.setId(profile.getId());
        dto.setUserId(profile.getUser().getId());
        dto.setUsername(profile.getUser().getFirstName() + " "+ profile.getUser().getLastName());
        dto.setLocation(profile.getLocation());
        dto.setJobTitle(profile.getJobTitle());

        return dto;
    }
}
