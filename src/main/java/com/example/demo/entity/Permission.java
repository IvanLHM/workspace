package com.example.demo.entity;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class Permission {
    private Long id;
    private String permissionName;
    private String description;
    private LocalDateTime createdAt;
}