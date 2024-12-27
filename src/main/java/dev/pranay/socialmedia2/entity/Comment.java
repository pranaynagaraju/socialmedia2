package dev.pranay.socialmedia2.entity;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.IdGeneratorType;
import org.hibernate.annotations.UuidGenerator;

@Entity
@Data
@Table(name = "comments")
public class Comment {
    @Id
    @UuidGenerator
    private String commentId;
    private int postId;
    private String userId;
    private String comment;
}
