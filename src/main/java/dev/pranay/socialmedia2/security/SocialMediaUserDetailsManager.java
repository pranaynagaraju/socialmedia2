package dev.pranay.socialmedia2.security;

import dev.pranay.socialmedia2.entity.Profile;
import dev.pranay.socialmedia2.repo.ProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SocialMediaUserDetailsManager implements UserDetailsService {

    @Autowired
    private ProfileRepo profileRepo;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       Optional<Profile> profileOptional= profileRepo.findByEmail(email);
        if(profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            return new User(profile.getEmail(),
                    profile.getPassword(),
                    List.of(new SimpleGrantedAuthority("USER")));
        }
        else
        {
            return null;
        }
//            throw new EmailNotFound(email);
    }

    public Profile createProfile (Profile profile)
    {
        return profileRepo.save(profile);
    };
}
