package dev.pranay.socialmedia2.service.impl;

import dev.pranay.socialmedia2.dto.PostBasicDetailsDto;
import dev.pranay.socialmedia2.dto.ProfileDto;
import dev.pranay.socialmedia2.dto.SearchUserDto;
import dev.pranay.socialmedia2.dto.UserDto;
import dev.pranay.socialmedia2.entity.Post;
import dev.pranay.socialmedia2.entity.Profile;
import dev.pranay.socialmedia2.entity.Save;
import dev.pranay.socialmedia2.exception.ProfileAlreadyExistsException;
import dev.pranay.socialmedia2.exception.ProfileNotFoundException;
import dev.pranay.socialmedia2.mapper.UserMapper;
import dev.pranay.socialmedia2.repo.PostRepo;
import dev.pranay.socialmedia2.repo.ProfileRepo;
import dev.pranay.socialmedia2.repo.SaveRepo;
import dev.pranay.socialmedia2.security.SocialMediaUserDetailsManager;
import dev.pranay.socialmedia2.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {
    @Autowired
    private SocialMediaUserDetailsManager socialMediaUserDetailsManager;
    @Autowired
    private ProfileRepo profileRepo;
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private SaveRepo saveRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean signUp(Profile profile) throws ProfileAlreadyExistsException {
        //TODO Implement pagination
        if (profileRepo.findByEmail(profile.getEmail()).isPresent()) {
            throw new ProfileAlreadyExistsException(profile.getEmail());
        }
        profile.setPassword(passwordEncoder.encode(profile.getPassword()));
        return socialMediaUserDetailsManager.createProfile(profile) != null;
    }

    @Override
    public UserDto getLoggedInUserDetails() throws ProfileNotFoundException {
        Profile profile = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return UserMapper.toDTO(profile);
    }


    @Override
    public ProfileDto getProfileDetailsByUserId(String userId) throws ProfileNotFoundException {
        ProfileDto profileDto = new ProfileDto();
        Optional<Profile> profileOptional = profileRepo.findById(userId);
        if (profileOptional.isPresent()) {
            Profile loggedInProfile = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Profile profile = profileOptional.get();
            profileDto.setEmail(profile.getEmail());
            profileDto.setUserName(profileDto.getUserName());
            //TODO Need to modify this corectly
            profileDto.setUserPhotoUrl("Dummy url");
            List<Post> posts = postRepo.findByProfile_UserIdOrderByCreatedOnDesc(profile.getUserId());
            List<PostBasicDetailsDto> postBasicDetailsDtoList = new ArrayList<>();
            for (Post post : posts) {
                PostBasicDetailsDto postBasicDetailsDto = new PostBasicDetailsDto();
                Optional<Post> postDetails = postRepo.findById(post.getPostId());
                if (postDetails.isPresent()) {
                    postBasicDetailsDto.setPostId(postDetails.get().getPostId());
                    postBasicDetailsDto.setPostImageUrl(postDetails.get().getImageUrl());
                    postBasicDetailsDto.setTotalLikes(postDetails.get().getTotalLikes());
                    postBasicDetailsDto.setTotalComments(postDetails.get().getTotalComments());
                }

                postBasicDetailsDtoList.add(postBasicDetailsDto);
            }
            profileDto.setUserPosts(postBasicDetailsDtoList);
            List<PostBasicDetailsDto> savedpostsBasicDetailsDtoList = new ArrayList<>();
            List<Save> saveList = null;
            if (loggedInProfile.getUserId().equals(profile.getUserId())) {
                saveList = saveRepo.findByProfile_UserIdOrderByCreatedOnDesc(loggedInProfile.getUserId());
                if (saveList != null) {
                    for (Save savePost : saveList) {
                        PostBasicDetailsDto postBasicDetailsDto = new PostBasicDetailsDto();
                        postBasicDetailsDto.setPostId(savePost.getPostId());
                        Optional<Post> savedPostDetails = postRepo.findById(postBasicDetailsDto.getPostId());
                        if (savedPostDetails.isPresent()) {
                            postBasicDetailsDto.setPostImageUrl(savedPostDetails.get().getImageUrl());
                            postBasicDetailsDto.setTotalLikes(savedPostDetails.get().getTotalLikes());
                            postBasicDetailsDto.setTotalComments(savedPostDetails.get().getTotalComments());
                            savedpostsBasicDetailsDtoList.add(postBasicDetailsDto);
                        }
                    }
                }
            }
            profileDto.setSavedPostsList(savedpostsBasicDetailsDtoList);
            return profileDto;
        }

        return profileDto;
    }

    @Override
    public List<SearchUserDto> searchUser(String search) {
        List<SearchUserDto> searchResults = new ArrayList<>();
        List<Profile> profiles = profileRepo.findByUsername(search);
        for (Profile profile : profiles) {
            SearchUserDto searchUserDto = new SearchUserDto();
            searchUserDto.setUid(profile.getUserId());
            searchUserDto.setUserName(profile.getUsername());
            searchUserDto.setEmail(profile.getEmail());
            //TODO need to correct this
            searchUserDto.setUserPhotoUrl("Dummy Url");
            searchResults.add(searchUserDto);
        }
        return searchResults;
    }


}

