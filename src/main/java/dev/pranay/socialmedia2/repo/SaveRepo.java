package dev.pranay.socialmedia2.repo;

import dev.pranay.socialmedia2.entity.Save;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaveRepo extends JpaRepository<Save,String> {
    Save findByPostIdAndUserId(int postId, String userId);
    List<Save> findByUserIdOrderByCreatedOnDesc(String uid);
}
