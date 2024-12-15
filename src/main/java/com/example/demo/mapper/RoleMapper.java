package com.example.demo.mapper;

import com.example.demo.entity.Role;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Set;

@Mapper
public interface RoleMapper {
    List<Role> findAll();

    Role findById(Long id);

    Set<Role> findByUserId(Long userId);

    void insert(Role role);

    void update(Role role);

    void delete(Long id);

    void insertRolePermission(Long roleId, Long permissionId);

    void deleteRolePermission(Long roleId, Long permissionId);
}