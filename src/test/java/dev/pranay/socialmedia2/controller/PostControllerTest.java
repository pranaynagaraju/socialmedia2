package dev.pranay.socialmedia2.controller;

import dev.pranay.socialmedia2.dto.PostDto;
import dev.pranay.socialmedia2.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;


import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class PostControllerTest {
@Mock
PostService postService;
@InjectMocks
PostController postController;

List<PostDto> allPosts;
@BeforeEach
public void setUp()
{
    MockitoAnnotations.openMocks(this);
    PostDto post1 = new PostDto(
            1,
            "https://example.com/image1.jpg",
            "Exploring the mountains!",
            150,
            true,
            12,
            Instant.now(),
            "Alice",
            "alice123",
            "https://example.com/profile1.jpg",
            true
    );

    PostDto post2 = new PostDto(
            2,
            "https://example.com/image2.jpg",
            "Sunset by the beach 🌅",
            230,
            false,
            34,
            Instant.now().minusSeconds(3600),
            "Bob",
            "bob456",
            "https://example.com/profile2.jpg",
            false
    );

    allPosts = List.of(post1, post2);

    when(postService.getAllPosts()).thenReturn(new ArrayList<PostDto>(allPosts));
    when(postService.likePost(1)).thenReturn("post liked");
}
    @Test
    public void testGetAllPosts() {
    //test data

        assertEquals(HttpStatus.OK,postController.getAllPosts().getStatusCode());
        assertEquals(allPosts,postController.getAllPosts().getBody());
    }

    @Test
    public void testlikePost() {
        //test data

        assertEquals(HttpStatus.CREATED, postController.likePost(1).getStatusCode());
        assertEquals("post liked", postController.likePost(1).getBody());

    }

}
