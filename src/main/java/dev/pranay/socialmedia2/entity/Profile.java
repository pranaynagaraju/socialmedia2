package dev.pranay.socialmedia2.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "profiles")
@Data
public class Profile implements UserDetails {
    @Id
    @UuidGenerator
    private String userId;
    private String username;
    private String email;
    private String password;
    @Transient
    private GrantedAuthority grantedAuthority;

    public Profile() {

    }

    public Profile(String userId, String username, String email, String password) {
        setUserId(userId);
        setUsername(username);
        setEmail(email);
        setPassword(password);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }
}
