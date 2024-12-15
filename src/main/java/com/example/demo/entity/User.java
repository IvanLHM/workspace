package com.example.demo.entity;

import lombok.Data;
import java.util.Set;

@Data
public class User {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Boolean enabled;
    private Set<Role> roles;
}