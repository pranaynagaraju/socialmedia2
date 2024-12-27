package dev.pranay.socialmedia2.repo;

import dev.pranay.socialmedia2.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ProfileRepo extends JpaRepository<Profile,String> {
    public Optional<Profile> findByEmail(String email);
}
