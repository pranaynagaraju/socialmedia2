package dev.pranay.socialmedia2.service.impl;
import dev.pranay.socialmedia2.entity.Profile;
import dev.pranay.socialmedia2.exception.ProfileAlreadyExistsException;
import dev.pranay.socialmedia2.repo.ProfileRepo;
import dev.pranay.socialmedia2.security.SocialMediaUserDetailsManager;
import dev.pranay.socialmedia2.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private SocialMediaUserDetailsManager socialMediaUserDetailsManager;

    @Autowired
    private ProfileRepo profileRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean signUp(Profile profile) throws ProfileAlreadyExistsException {
        //TODO Implement pagination
        if(profileRepo.findByEmail(profile.getEmail()).isPresent()){
            throw new ProfileAlreadyExistsException(profile.getEmail());
        }
        profile.setPassword(passwordEncoder.encode(profile.getPassword()));
        return socialMediaUserDetailsManager.createProfile(profile) != null;
    }
}
