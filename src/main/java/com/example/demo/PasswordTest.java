package com.example.demo;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordTest {
    public static void main(String[] args) {
        // 创建密码编码器
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // 要验证的密码和哈希值
        String rawPassword = "admin";
        String hashedPassword = "$2a$10$huns716K0Q0h0IB7LVRoGeA3t1F6S2ixDelTD7Qx63r.mJIVm8Ztq";

        // 验证密码
        boolean matches = encoder.matches(rawPassword, hashedPassword);
        System.out.println("密码 'admin' 与哈希值匹配结果: " + matches);

        // 生成新的哈希值（用于参考）
        String newHashedPassword = encoder.encode(rawPassword);
        System.out.println("新生成的哈希值: " + newHashedPassword);

        // 验证新生成的哈希值
        boolean newMatches = encoder.matches(rawPassword, newHashedPassword);
        System.out.println("密码 'admin' 与新哈希值匹配结果: " + newMatches);
    }
}