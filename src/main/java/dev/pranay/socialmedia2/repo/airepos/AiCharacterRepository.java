package dev.pranay.socialmedia2.repo.airepos;

import dev.pranay.socialmedia2.entity.ai.AiCharacter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AiCharacterRepository extends JpaRepository<AiCharacter, Integer> {
}