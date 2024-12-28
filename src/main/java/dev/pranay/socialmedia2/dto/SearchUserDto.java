package dev.pranay.socialmedia2.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SearchUserDto {
    private String uid;
    private String userName;
    private String email;
    private String userPhotoUrl;

}
