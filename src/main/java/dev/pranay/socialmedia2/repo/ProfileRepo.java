package dev.pranay.socialmedia2.repo;

import dev.pranay.socialmedia2.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ProfileRepo extends JpaRepository<Profile, String> {
    public Optional<Profile> findByEmail(String email);

    @Query(value = "SELECT * FROM socialmedia.profiles WHERE username ILIKE :username || '%'", nativeQuery = true)
    List<Profile> findByUsername(@Param("username") String username);
}
