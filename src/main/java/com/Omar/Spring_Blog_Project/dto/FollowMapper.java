package com.Omar.Spring_Blog_Project.dto;

import com.Omar.Spring_Blog_Project.model.Follow;
import org.springframework.stereotype.Component;

@Component
public class FollowMapper {
    public FollowResponse toDtoFollowers (Follow follow) {
        FollowResponse followResponse = new FollowResponse(
                follow.getId(),
                follow.getFollower().getId(),
                follow.getFollower().getFirstName() + " " + follow.getFollower().getLastName()
        );
        return followResponse;
    }

    public FollowResponse toDtoFollowing (Follow follow) {
        FollowResponse followResponse = new FollowResponse(
                follow.getId(),
                follow.getFollowing().getId(),
                follow.getFollowing().getFirstName() + " " + follow.getFollowing().getLastName()
        );
        return followResponse;
    }
}
