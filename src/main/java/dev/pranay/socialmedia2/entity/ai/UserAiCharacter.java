package dev.pranay.socialmedia2.entity.ai;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_ai_characters", schema = "socialmedia",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "ai_character_id"}))
@Getter
@Setter
public class UserAiCharacter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @ManyToOne
    @JoinColumn(name = "ai_character_id", nullable = false, referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "user_ai_characters_ai_character_id_fkey"))
    private AiCharacter aiCharacter;
}