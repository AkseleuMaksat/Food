INSERT INTO t_users (email, password, full_name) VALUES ('moderator@mail.com', '$2a$10$g.h.i.j.k.l.m.n.o.p.q.r', 'Moderator User');
INSERT INTO t_users_roles (user_id, role_id) VALUES ((SELECT id FROM t_users WHERE email = 'moderator@mail.com'), (SELECT id FROM t_roles WHERE role = 'ROLE_MODERATOR'));
