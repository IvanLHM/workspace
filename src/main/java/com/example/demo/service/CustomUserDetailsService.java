package com.example.demo.service;

import com.example.demo.entity.Permission;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.mapper.RoleMapper;
import com.example.demo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("正在加载用户: {}", username);

        User user = userMapper.findByUsername(username);
        if (user == null) {
            log.error("用户不存在: {}", username);
            throw new UsernameNotFoundException("用户不存在: " + username);
        }

        log.info("找到用户: {}, ID: {}", user.getUsername(), user.getId());

        Set<SimpleGrantedAuthority> authorities = new HashSet<>();

        // 添加用户角色
        if (user.getRoles() != null) {
            log.info("用户 {} 的角色数量: {}", username, user.getRoles().size());

            for (Role role : user.getRoles()) {
                log.info("处理角色: {}, ID: {}", role.getRoleName(), role.getId());
                authorities.add(new SimpleGrantedAuthority(role.getRoleName()));

                // 获取角色的权限
                Role fullRole = roleMapper.findById(role.getId());
                if (fullRole != null && fullRole.getPermissions() != null) {
                    log.info("角色 {} 的权限数量: {}", role.getRoleName(), fullRole.getPermissions().size());
                    authorities.addAll(fullRole.getPermissions().stream()
                            .map(Permission::getPermissionName)
                            .peek(permName -> log.info("添加权限: {}", permName))
                            .map(SimpleGrantedAuthority::new)
                            .collect(Collectors.toSet()));
                }
            }
        } else {
            log.warn("用户 {} 没有任何角色", username);
        }

        log.info("用户 {} 的最终权限: {}",
                username,
                authorities.stream()
                        .map(SimpleGrantedAuthority::getAuthority)
                        .collect(Collectors.joining(", ")));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getEnabled(),
                true,
                true,
                true,
                authorities);
    }
}