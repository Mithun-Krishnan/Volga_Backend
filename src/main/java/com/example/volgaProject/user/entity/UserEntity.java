package com.example.volgaProject.user.entity;

import com.example.volgaProject.user.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Table(name = "users")
public class UserEntity  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private boolean isAccountNonExpired = true;

    private boolean isCredentialsNonExpired=true;

    private boolean isEnabled=true;

    @Column()
    private boolean isAccountNonLocked=true;

    @Enumerated(EnumType.STRING)
    private Role role;
}
