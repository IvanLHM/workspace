package com.example.demo.mapper;

import com.example.demo.entity.Permission;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Set;

@Mapper
public interface PermissionMapper {
    List<Permission> findAll();

    Permission findById(Long id);

    Set<Permission> findByRoleId(Long roleId);

    void insert(Permission permission);

    void update(Permission permission);

    void delete(Long id);
}