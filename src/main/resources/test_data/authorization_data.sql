INSERT INTO users (username, password, salt, enabled) VALUES ('admin', 'admin', '123', 1);
INSERT INTO users (username, password, salt, enabled) VALUES ('user', 'user', '123', 1);
INSERT INTO users (username, password, salt, enabled) VALUES ('zhuyu', 'zhuyu', '123', 1);


INSERT INTO authorities VALUES 'admin', 'ROLE_ADMIN';
INSERT INTO authorities VALUES 'admin', 'ROLE_USER';
INSERT INTO authorities VALUES 'user', 'ROLE_USER';
INSERT INTO authorities VALUES 'zhuyu', 'ROLE_USER';