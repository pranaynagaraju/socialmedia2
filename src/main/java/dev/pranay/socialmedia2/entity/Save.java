package dev.pranay.socialmedia2.entity;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
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
    private String userId;
    private Instant createdOn;
   }