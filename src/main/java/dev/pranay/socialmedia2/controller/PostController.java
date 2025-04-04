package dev.pranay.socialmedia2.controller;

import dev.pranay.socialmedia2.dto.PostDetailsDto;
import dev.pranay.socialmedia2.dto.PostDto;
import dev.pranay.socialmedia2.exception.PostNotUploadedException;
import dev.pranay.socialmedia2.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@CrossOrigin(origins = "https://orbitsocials.netlify.app")
public class PostController {
    PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file, @RequestParam("postText") String postText) throws PostNotUploadedException, IOException {
        String result = postService.uploadPost(file, postText);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/get-all-posts")
    public ResponseEntity<List<PostDto>> getAllPosts() {
        return new ResponseEntity<>(postService.getAllPosts(), HttpStatus.OK);
    }

    @PostMapping("/like")
    public ResponseEntity<String> likePost(@RequestParam("postId") int postId) {
        return new ResponseEntity<>(postService.likePost(postId), HttpStatus.CREATED);
    }

    @PostMapping("/save")
    public ResponseEntity<String> savePost(@RequestParam("postId") int postId) {
        return new ResponseEntity<>(postService.savePost(postId), HttpStatus.CREATED);
    }

    @GetMapping("/get-post-details")
    public ResponseEntity<PostDetailsDto> getPostDetails(@RequestParam("postId") int postId) {
        return new ResponseEntity<>(postService.getPostDetails(postId), HttpStatus.OK);
    }

    @PostMapping("/add-comment")
    public ResponseEntity<String> addComment(@RequestParam("postId") int postId, @RequestParam("comment") String comment) {
        return new ResponseEntity<>(postService.addComment(postId, comment), HttpStatus.CREATED);
    }
}
