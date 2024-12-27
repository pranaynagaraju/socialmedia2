package dev.pranay.socialmedia2.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;


@Entity
@Data
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;
    private String userId;
    private String imageUrl;
    private String postText;
    private Instant createdOn;
    private int totalLikes;
    private int totalComments;
}
