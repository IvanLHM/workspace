package com.example.demo.entity;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class Role {
    private Long id;
    private String roleName;
    private String description;
    private LocalDateTime createdAt;
    private Set<Permission> permissions;
}