package dev.pranay.socialmedia2.repo;

import dev.pranay.socialmedia2.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {

   List<Post> findByUserIdOrderByCreatedOnDesc(String uid);
}
