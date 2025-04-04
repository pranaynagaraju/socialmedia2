package dev.pranay.socialmedia2.security;

import dev.pranay.socialmedia2.entity.Profile;
import dev.pranay.socialmedia2.repo.ProfileRepo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SocialMediaUserDetailsManager implements UserDetailsService {


    private final ProfileRepo profileRepo;

    public SocialMediaUserDetailsManager(ProfileRepo profileRepo) {
        this.profileRepo = profileRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Profile> profileOptional = profileRepo.findByEmail(email);
        if (profileOptional.isPresent()) {
            Profile profile = profileOptional.get();
            return new Profile(profile.getUserId(),
                    profile.getUsername(),
                    profile.getEmail(),
                    profile.getPassword(),
                    profile.getUserImage());
        } else {
            throw new UsernameNotFoundException(email);
        }
    }

    public Profile createProfile(Profile profile) {
        return profileRepo.save(profile);
    }


}
