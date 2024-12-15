package com.example.demo.service;

import com.example.demo.entity.Role;
import java.util.List;
import java.util.Set;

public interface RoleService {
    List<Role> findAll();

    Role findById(Long id);

    Set<Role> findByUserId(Long userId);

    void create(Role role);

    void update(Role role);

    void delete(Long id);

    void assignPermission(Long roleId, Long permissionId);

    void removePermission(Long roleId, Long permissionId);
}