package dev.pranay.socialmedia2.mapper;

import dev.pranay.socialmedia2.dto.UserDto;
import dev.pranay.socialmedia2.entity.Profile;

public class UserMapper {
    public static UserDto toDTO(Profile profile) {
        UserDto userDto = new UserDto();
        userDto.setId(profile.getUserId());
        userDto.setName(profile.getUsername());
        userDto.setEmail(profile.getEmail());
        //TODO need to set this correctly
        userDto.setPhotoURL("Dummy url");
        return userDto;
    }
}
