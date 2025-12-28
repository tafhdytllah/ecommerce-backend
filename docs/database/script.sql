DROP TABLE users;

CREATE TABLE users
(
    id            VARCHAR(64) PRIMARY KEY,
    username      VARCHAR(50) UNIQUE  NOT NULL,
    email         VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255)        NOT NULL,
    role          VARCHAR(20)         NOT NULL,
    is_active     BOOLEAN             NOT NULL DEFAULT TRUE,
    created_at    TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by    VARCHAR(64)         NOT NULL,
    updated_at    TIMESTAMP           NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_by    VARCHAR(64)         NOT NULL,
    deleted_at    TIMESTAMP,
    deleted_by    VARCHAR(64)
);

CREATE UNIQUE INDEX ux_users_username ON users(username);
CREATE UNIQUE INDEX ux_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);

INSERT INTO users (id, username, email, password_hash, role, created_by, updated_by)
VALUES ('SYSTEM_ADMIN',
        'admin',
        'admin@system.local',
        '$2a$10$hashedpassword',
        'ADMIN',
        'SYSTEM',
        'SYSTEM');

SELECT * FROM users;

DELETE FROM users where username = 'johndo';