package dev.pranay.socialmedia2.entity.ai;

import dev.pranay.socialmedia2.entity.Profile;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ai_characters", schema = "socialmedia")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String imageUrl;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String personality;

    // One-to-One relationship with Profile
    @OneToOne
    @JoinColumn(name = "profile_id", referencedColumnName = "userId") // Foreign key
    private Profile profile;
}
