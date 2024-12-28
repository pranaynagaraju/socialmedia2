package dev.pranay.socialmedia2.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CommentDto {

    private String commentedUserName;
    private String commentedUserPhoto;
    private String comment;
    private String commentId;

}
