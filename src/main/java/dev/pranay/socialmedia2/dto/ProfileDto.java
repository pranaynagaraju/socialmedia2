package dev.pranay.socialmedia2.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ProfileDto {

    private String userName;
    private String email;
    private String userPhotoUrl;

    private List<PostBasicDetailsDto> userPosts;

    private List<PostBasicDetailsDto> savedPostsList;


}
