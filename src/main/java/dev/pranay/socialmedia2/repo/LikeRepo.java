package dev.pranay.socialmedia2.repo;
import dev.pranay.socialmedia2.entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepo extends JpaRepository<Like,String> {
    Like findByPostIdAndUserId(int postId, String userId);

}
