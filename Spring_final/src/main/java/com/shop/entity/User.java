package com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    private String password;
    
    @Column(unique = true)
    private String email;
    
    private String nickname;

    @Enumerated(EnumType.STRING)
    private Role role = Role.USER; // 기본값은 USER

    public boolean isAdmin() {
        return role == Role.ADMIN;
    }
} 