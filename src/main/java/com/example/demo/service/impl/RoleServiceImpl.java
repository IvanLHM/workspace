package com.example.demo.service.impl;

import com.example.demo.entity.Role;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public List<Role> findAll() {
        return roleMapper.findAll();
    }

    @Override
    public Role findById(Long id) {
        return roleMapper.findById(id);
    }

    @Override
    public Set<Role> findByUserId(Long userId) {
        return roleMapper.findByUserId(userId);
    }

    @Override
    @Transactional
    public void create(Role role) {
        roleMapper.insert(role);
    }

    @Override
    @Transactional
    public void update(Role role) {
        roleMapper.update(role);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        roleMapper.delete(id);
    }

    @Override
    @Transactional
    public void assignPermission(Long roleId, Long permissionId) {
        roleMapper.insertRolePermission(roleId, permissionId);
    }

    @Override
    @Transactional
    public void removePermission(Long roleId, Long permissionId) {
        roleMapper.deleteRolePermission(roleId, permissionId);
    }
}