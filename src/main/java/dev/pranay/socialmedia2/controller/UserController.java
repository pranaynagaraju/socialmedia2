package dev.pranay.socialmedia2.controller;

import dev.pranay.socialmedia2.entity.Profile;
import dev.pranay.socialmedia2.exception.ProfileAlreadyExistsException;
import dev.pranay.socialmedia2.security.SocialMediaUserDetailsManager;
import dev.pranay.socialmedia2.service.ProfileService;
import dev.pranay.socialmedia2.service.impl.ProfileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpResponse;

@RestController
public class UserController {

    @Autowired
    private ProfileService profileService;
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody Profile profile) throws ProfileAlreadyExistsException {
        boolean isProfileCreated = profileService.signUp(profile);
        if(isProfileCreated)
        {
            return new ResponseEntity<>("Profile Created Successfully", HttpStatus.CREATED);
        }
        return new ResponseEntity<>("Profile couldn't be created", HttpStatus.BAD_REQUEST);
    }
}
