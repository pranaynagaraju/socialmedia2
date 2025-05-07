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

    public PostDto(int postId, String imageUrl, String postText, int totalLikes, boolean saved,
                   int totalComments, Instant createdOn, String name, String profileId,
                   String profilePicture, boolean liked) {
        this.postId = postId;
        this.imageUrl = imageUrl;
        this.postText = postText;
        this.totalLikes = totalLikes;
        this.saved = saved;
        this.totalComments = totalComments;
        this.createdOn = createdOn;
        this.name = name;
        this.profileId = profileId;
        this.profilePicture = profilePicture;
        this.liked = liked;
    }
    public PostDto()
    {

    }
}
