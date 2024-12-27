package dev.pranay.socialmedia2.entity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
@Table(name = "likes")
//TODO can add user to likes relationship
public class Like {
    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private String likeId;
    private int postId;
    private String userId;
}
