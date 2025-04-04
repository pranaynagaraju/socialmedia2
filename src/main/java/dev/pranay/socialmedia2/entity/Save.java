package dev.pranay.socialmedia2.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;

@Entity
@Data
@Table(name = "save")
public class Save {

    @Id
    @UuidGenerator
    @Column(updatable = false, nullable = false)
    private String savePostId;
    private int postId;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private Profile profile;
    private Instant createdOn;
}