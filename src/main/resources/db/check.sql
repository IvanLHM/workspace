-- 检查用户表
SELECT * FROM users;

-- 检查角色表
SELECT * FROM roles;

-- 检查用户-角色关联
SELECT u.username, r.role_name
FROM
    users u
    JOIN user_roles ur ON u.id = ur.user_id
    JOIN roles r ON r.id = ur.role_id;