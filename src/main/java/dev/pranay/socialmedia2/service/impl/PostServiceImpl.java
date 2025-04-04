package dev.pranay.socialmedia2.service.impl;

import dev.pranay.socialmedia2.dto.CommentDto;
import dev.pranay.socialmedia2.dto.PostDetailsDto;
import dev.pranay.socialmedia2.dto.PostDto;
import dev.pranay.socialmedia2.entity.*;
import dev.pranay.socialmedia2.entity.ai.AiCharacter;
import dev.pranay.socialmedia2.exception.PostNotUploadedException;
import dev.pranay.socialmedia2.repo.CommentRepo;
import dev.pranay.socialmedia2.repo.LikeRepo;
import dev.pranay.socialmedia2.repo.PostRepo;
import dev.pranay.socialmedia2.repo.SaveRepo;
import dev.pranay.socialmedia2.service.PostService;
import dev.pranay.socialmedia2.service.aiservice.AiCharacterService;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepo postRepo;
    private final LikeRepo likeRepo;
    private final CloudinaryService cloudinaryService;
    private final CommentRepo commentRepo;
    private final SaveRepo saveRepo;
    private final AiCharacterService aiCharacterService;

    public PostServiceImpl(PostRepo postRepo, LikeRepo likeRepo,
                           CloudinaryService cloudinaryService,
                           CommentRepo commentRepo,
                           SaveRepo saveRepo,
                           AiCharacterService aiCharacterService) {
        this.postRepo = postRepo;
        this.likeRepo = likeRepo;
        this.cloudinaryService = cloudinaryService;
        this.commentRepo = commentRepo;
        this.saveRepo = saveRepo;
        this.aiCharacterService=aiCharacterService;
    }

    @Override

    public String uploadPost(MultipartFile image, String postText) throws PostNotUploadedException {
        try {
            Profile profile = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            String cloudinaryUrl = cloudinaryService.uploadImage(image);
            Post post = new Post();
            post.setCreatedOn(Instant.now());
            post.setPostText(postText);
            post.setProfile(profile);
            post.setImageUrl(cloudinaryUrl);
            Post savedPost= postRepo.save(post);
            //aiCharacterService.generateAiComments(savedPost.getPostId(),postText);
            new Thread(()->aiCharacterService.generateAiComments(savedPost.getPostId(),postText,profile)).start();
            return "Post uploaded";
        } catch (Exception e) {
            throw new PostNotUploadedException();
        }
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> postsList = postRepo.findAll(Sort.by(Sort.Direction.DESC, "postId"));
        List<PostDto> postsDtoList = new ArrayList<>();
        Profile profile = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        for (Post post : postsList) {
            PostDto postDto = new PostDto();
            postDto.setName(post.getProfile().getUsername());
            postDto.setProfileId(post.getProfile().getUserId());
            postDto.setProfilePicture(post.getProfile().getUserImage());
            postDto.setPostId(post.getPostId());
            postDto.setImageUrl(post.getImageUrl());
            postDto.setPostText(post.getPostText());
            postDto.setTotalLikes(post.getTotalLikes());
            postDto.setCreatedOn(post.getCreatedOn());
            postDto.setTotalComments(post.getTotalComments());
            postDto.setLiked(likeRepo.findByPostIdAndProfile_UserId(postDto.getPostId(), profile.getUserId()) != null);
            postDto.setSaved(saveRepo.findByPostIdAndProfile_UserId(postDto.getPostId(), profile.getUserId()) != null);
            postsDtoList.add(postDto);
        }

        return postsDtoList;
    }


    @Override
    public String likePost(int postId) {
        Profile profile = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String response = "";
        Optional<Post> existingPost = postRepo.findById(postId);
        Like existingLike = likeRepo.findByPostIdAndProfile_UserId(postId, profile.getUserId());
        if (existingLike == null) {
            Like newLike = new Like();
            newLike.setPostId(postId);
            newLike.setProfile(profile);
            likeRepo.save(newLike);
            if (existingPost.isPresent()) {
                Post post = existingPost.get();
                post.setTotalLikes(post.getTotalLikes() + 1);
                postRepo.save(post);
            }
            response = "post liked";
        } else {
            likeRepo.delete(existingLike);
            if (existingPost.isPresent()) {
                Post post = existingPost.get();
                post.setTotalLikes(post.getTotalLikes() - 1);
                postRepo.save(post);
            }
            response = "post disliked";
        }
        return response;
    }

    @Override
    public String savePost(int postId) {
        Profile profile = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Save existingSave = saveRepo.findByPostIdAndProfile_UserId(postId, profile.getUserId());
        if (existingSave == null) {
            Save save = new Save();
            save.setPostId(postId);
            save.setCreatedOn(Instant.now());
            save.setProfile(profile);
            saveRepo.save(save);
            return "Post saved";
        } else {
            saveRepo.delete(existingSave);
            return "Post unsaved";
        }

    }

    @Override
    public String addComment(int postId, String comment) {
        Profile profile = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment commentObj = new Comment();
        commentObj.setProfile(profile);
        commentObj.setPostId(postId);
        commentObj.setComment(comment);
        commentRepo.save(commentObj);
        postRepo.findById(postId)
                .ifPresent(post -> {
                    post.setTotalComments(post.getTotalComments() + 1);
                    postRepo.save(post);
                });
        return "Comment Saved";
    }

    @Override
    public String addAiComment(int postId, String comment, AiCharacter aiCharacter) {
        Profile profile = aiCharacter.getProfile();
        Comment commentObj = new Comment();
        commentObj.setProfile(profile);
        commentObj.setPostId(postId);
        commentObj.setComment(comment);
        commentRepo.save(commentObj);
        postRepo.findById(postId)
                .ifPresent(post -> {
                    post.setTotalComments(post.getTotalComments() + 1);
                    postRepo.save(post);
                });
        return "Comment Saved";
    }

    @Override
    public PostDetailsDto getPostDetails(int postId) {
        Profile profile = (Profile) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        PostDetailsDto postDetailsDto = new PostDetailsDto();
        List<CommentDto> commentDtoList = new ArrayList<>();
        List<Comment> comments = commentRepo.findByPostId(postId);
        postRepo.findById(postId)
                .ifPresent(post -> {
                    postDetailsDto.setPostId(postId);
                    postDetailsDto.setPostText(post.getPostText());
                    postDetailsDto.setPostImageUrl(post.getImageUrl());
                    postDetailsDto.setTotalComments(post.getTotalComments());
                    postDetailsDto.setTotalLikes(post.getTotalLikes());
                    postDetailsDto.setLiked(likeRepo.findByPostIdAndProfile_UserId(postId, profile.getUserId()) != null);
                    postDetailsDto.setSaved(saveRepo.findByPostIdAndProfile_UserId(postId, profile.getUserId()) != null);
                    postDetailsDto.setPostUploadedByUserName(post.getProfile().getUsername());
                    postDetailsDto.setPostUploadedByUserPhoto(post.getProfile().getUserImage());

                });
        for (Comment comment : comments) {
            CommentDto commentDto = new CommentDto();
            commentDto.setCommentedUserName(comment.getProfile().getUsername());
            commentDto.setCommentedUserPhoto(comment.getProfile().getUserImage());
            commentDto.setComment(comment.getComment());
            commentDto.setCommentId(comment.getCommentId());
            commentDto.setCommentedUserId(comment.getProfile().getUserId());
            commentDtoList.add(commentDto);
        }
        postDetailsDto.setAllComments(commentDtoList);
        return postDetailsDto;
    }

}
