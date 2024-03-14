package com.github.zethi.monkeytypebackendclone.entity;

import com.github.zethi.monkeytypebackendclone.utils.constant.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Email(message = "Email field is obligatory")
    @Size(max = 320)
    @Column(name = "email", length = 320, unique = true, nullable = false)
    private String email;

    @NotBlank(message = "Username field is obligatory")
    @Size(min = 5, max = 40)
    @Column(name = "username", length = 40, unique = true, nullable = false)
    private String username;

    @NotBlank(message = "Password field is obligatory")
    @Size(min = 5, max = 60)
    @Column(name = "password", length = 60, nullable = false )
    private String password;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER, targetClass = Role.class)
    private Set<Role> roles;

    @OneToOne(targetEntity = Stats.class, cascade = CascadeType.ALL)
    private Stats stats;

    public UUID getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Stats getStats() {
        return stats;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}