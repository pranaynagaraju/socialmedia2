package dev.pranay.socialmedia2.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Setter
@Getter
public class PostDto {
    private int postId;
    private String imageUrl;
    private String postText;
    private int totalLikes;
    private boolean saved;
    private int totalComments;
    private Instant createdOn;
    private String name;
    private String profileId;
    private String profilePicture;
    private boolean liked;
}
