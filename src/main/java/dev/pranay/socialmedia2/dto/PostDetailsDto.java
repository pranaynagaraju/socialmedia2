package dev.pranay.socialmedia2.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class PostDetailsDto {

    private int postId;
    private String postText;
    private String postImageUrl;

    private Boolean saved;
    private boolean liked;

    private String postUploadedByUserName;

    private String postUploadedByUserPhoto;

    private List<CommentDto> allComments;

    private int totalLikes;
    private int totalComments;

}
