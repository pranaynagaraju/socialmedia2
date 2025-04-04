package dev.pranay.socialmedia2.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserDto {
    private String id;
    private String email;
    private String name;
    private String photoURL;
}
