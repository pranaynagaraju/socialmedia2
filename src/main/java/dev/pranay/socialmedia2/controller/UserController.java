package dev.pranay.socialmedia2.controller;

import dev.pranay.socialmedia2.entity.Profile;
import dev.pranay.socialmedia2.security.SocialMediaUserDetailsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private SocialMediaUserDetailsManager socialMediaUserDetailsManager;
    @PostMapping("/signup")
 public String signup(@RequestBody Profile profile)
    {

        return socialMediaUserDetailsManager.createProfile(profile)!=null?"CREATED":"Something went wrong";

    }
}
