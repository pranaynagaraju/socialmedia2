package dev.pranay.socialmedia2.controller;

import dev.pranay.socialmedia2.dto.ProfileDto;
import dev.pranay.socialmedia2.dto.SearchUserDto;
import dev.pranay.socialmedia2.dto.UserDto;
import dev.pranay.socialmedia2.entity.Profile;
import dev.pranay.socialmedia2.exception.ProfileAlreadyExistsException;
import dev.pranay.socialmedia2.exception.ProfileNotFoundException;
import dev.pranay.socialmedia2.service.ProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Profile profile) throws ProfileAlreadyExistsException {
        boolean isProfileCreated = profileService.signUp(profile);
        if (isProfileCreated) {
            return new ResponseEntity<>("Profile Created Successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Profile couldn't be created", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/user-details")
    public ResponseEntity<UserDto> user() throws ProfileNotFoundException {
        UserDto userDto = profileService.getLoggedInUserDetails();
        if (userDto != null) {
            return new ResponseEntity<>(userDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(userDto, HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping("/user-profile")
    public ResponseEntity<ProfileDto> userProfile(@RequestParam String uid) throws ProfileNotFoundException {
        ProfileDto profileDto = profileService.getProfileDetailsByUserId(uid);
        return new ResponseEntity<>(profileDto, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SearchUserDto>> searchUserProfile(@RequestParam String q) {
        List<SearchUserDto> searchUserDto = profileService.searchUser(q.toLowerCase());
        return new ResponseEntity<>(searchUserDto, HttpStatus.OK);
    }

    @GetMapping("/authenticate")
    public ResponseEntity<Boolean> authenticate() {
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
