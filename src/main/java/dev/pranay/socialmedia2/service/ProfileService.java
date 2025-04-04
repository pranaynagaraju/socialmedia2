package dev.pranay.socialmedia2.service;

import dev.pranay.socialmedia2.dto.ProfileDto;
import dev.pranay.socialmedia2.dto.SearchUserDto;
import dev.pranay.socialmedia2.dto.UserDto;
import dev.pranay.socialmedia2.entity.Profile;
import dev.pranay.socialmedia2.exception.ProfileAlreadyExistsException;
import dev.pranay.socialmedia2.exception.ProfileNotFoundException;

import java.util.List;

public interface ProfileService {

    boolean signUp(Profile profile) throws ProfileAlreadyExistsException;

    UserDto getLoggedInUserDetails() throws ProfileNotFoundException;

    ProfileDto getProfileDetailsByUserId(String uid) throws ProfileNotFoundException;

    List<SearchUserDto> searchUser(String search);
}
