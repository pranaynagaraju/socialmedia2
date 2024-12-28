package dev.pranay.socialmedia2.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PostBasicDetailsDto {
    private int postId;
    private String postImageUrl;
    private int totalLikes;
    private int totalComments;

}
