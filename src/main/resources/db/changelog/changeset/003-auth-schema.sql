CREATE TABLE t_roles (
    id BIGSERIAL PRIMARY KEY,
    role VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE t_users (
    id BIGSERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    full_name VARCHAR(255)
);

CREATE TABLE t_users_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_user_role_user FOREIGN KEY (user_id) REFERENCES t_users(id),
    CONSTRAINT fk_user_role_role FOREIGN KEY (role_id) REFERENCES t_roles(id)
);

INSERT INTO t_roles (role) VALUES ('ROLE_USER'), ('ROLE_ADMIN'), ('ROLE_MODERATOR');

INSERT INTO t_users (email, password, full_name) VALUES ('admin@gmail.com', '$2a$10$LQWAhuaSMo7ESTHKz3F3ge4uFP3okVhQQXaAHeUw1eLK7b7XA4gfu', 'Admin');
INSERT INTO t_users (email, password, full_name) VALUES ('moderator@gmail.com', '$2a$10$LQWAhuaSMo7ESTHKz3F3ge4uFP3okVhQQXaAHeUw1eLK7b7XA4gfu', 'Moderator User');

INSERT INTO t_users_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO t_users_roles (user_id, role_id) VALUES ((SELECT id FROM t_users WHERE email = 'moderator@gmail.com'), (SELECT id FROM t_roles WHERE role = 'ROLE_MODERATOR'));
