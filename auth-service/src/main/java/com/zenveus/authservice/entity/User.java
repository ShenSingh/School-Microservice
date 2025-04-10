package com.zenveus.authservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user") // optional, you can customize table name
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private  String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String role; // "ROLE_USER" or "ROLE_ADMIN"
}
