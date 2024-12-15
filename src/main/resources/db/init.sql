-- 用户表
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    email VARCHAR(100),
    enabled BOOLEAN DEFAULT true
);

-- 角色表
CREATE TABLE IF NOT EXISTS roles (
    id SERIAL PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(200)
);

-- 权限表
CREATE TABLE IF NOT EXISTS permissions (
    id SERIAL PRIMARY KEY,
    permission_name VARCHAR(50) NOT NULL UNIQUE,
    description VARCHAR(200)
);

-- 用户-角色关联表
CREATE TABLE IF NOT EXISTS user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

-- 角色-权限关联表
CREATE TABLE IF NOT EXISTS role_permissions (
    role_id BIGINT NOT NULL,
    permission_id BIGINT NOT NULL,
    PRIMARY KEY (role_id, permission_id),
    FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE,
    FOREIGN KEY (permission_id) REFERENCES permissions (id) ON DELETE CASCADE
);

-- 插入基础角色
INSERT INTO
    roles (role_name, description)
VALUES ('ROLE_ADMIN', '系统管理员'),
    ('ROLE_USER', '普通用户')
ON CONFLICT (role_name) DO NOTHING;

-- 插入基础权限
INSERT INTO
    permissions (permission_name, description)
VALUES ('user:view', '查看用户'),
    ('user:create', '创建用户'),
    ('user:edit', '编辑用户'),
    ('user:delete', '删除用户'),
    ('role:view', '查看角色'),
    ('role:create', '创建角色'),
    ('role:edit', '编辑角色'),
    ('role:delete', '删除角色'),
    ('permission:view', '查看权限')
ON CONFLICT (permission_name) DO NOTHING;

-- 为管理员角色分配所有权限
INSERT INTO
    role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE
    r.role_name = 'ROLE_ADMIN'
ON CONFLICT DO NOTHING;

-- 为普通用户角色分配基本权限
INSERT INTO
    role_permissions (role_id, permission_id)
SELECT r.id, p.id
FROM roles r, permissions p
WHERE
    r.role_name = 'ROLE_USER'
    AND p.permission_name IN ('user:view')
ON CONFLICT DO NOTHING;

-- 插入默认管理员用户
INSERT INTO
    users (
        username,
        password,
        email,
        enabled
    )
VALUES (
        'admin',
        '$2a$10$rXXm3vjzGYw.dK.WlGJ/3.YQT.lMOV.6.VJm2K4RTYmZCGNhXUUHy',
        'admin@example.com',
        true
    )
ON CONFLICT (username) DO NOTHING;

-- 为默认管理员用户分配管理员角色
INSERT INTO
    user_roles (user_id, role_id)
SELECT u.id, r.id
FROM users u, roles r
WHERE
    u.username = 'admin'
    AND r.role_name = 'ROLE_ADMIN'
ON CONFLICT DO NOTHING;