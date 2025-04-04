package dev.pranay.socialmedia2.repo.airepos;


import dev.pranay.socialmedia2.entity.ai.UserAiCharacter;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserAiCharacterRepository extends JpaRepository<UserAiCharacter, Integer> {
    List<UserAiCharacter> findByUserId(String userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM UserAiCharacter u WHERE u.userId = :userId")
    void deleteAllByUserId(@Param("userId") String userId);
}