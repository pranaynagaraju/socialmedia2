package dev.pranay.socialmedia2.service;

//import com.techbypranay.socialmediaapp.dto.PostDetailsDto;

import dev.pranay.socialmedia2.dto.PostDetailsDto;
import dev.pranay.socialmedia2.dto.PostDto;
import dev.pranay.socialmedia2.exception.PostNotUploadedException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface PostService {
    String uploadPost(MultipartFile image, String postText) throws IOException, PostNotUploadedException;

    List<PostDto> getAllPosts();

    String likePost(int postId);

    String savePost(int postId);

    String addComment(int postId, String comment);

    PostDetailsDto getPostDetails(int postId);
}

