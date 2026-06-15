package org.taskmanagement.authservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.taskmanagement.authservice.status.AccountStatus;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "auth_user")
@Getter
@Setter
@ToString(exclude = {"password"})
@NoArgsConstructor
@Builder
@SQLRestriction(value = "account_status = 'ACTIVE' ")
@AllArgsConstructor
public class Auth implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long id;
    @Column(unique = true,nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @UpdateTimestamp
    private LocalDateTime lastLoginAt;
    private String accountStatus =   AccountStatus.ACTIVE.name();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
