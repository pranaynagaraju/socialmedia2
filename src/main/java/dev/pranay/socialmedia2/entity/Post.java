package dev.pranay.socialmedia2.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;


@Setter
@Getter
@Entity
@Data
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int postId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private Profile profile;
    private String imageUrl;
    private String postText;
    private Instant createdOn;
    private int totalLikes;
    private int totalComments;
}
