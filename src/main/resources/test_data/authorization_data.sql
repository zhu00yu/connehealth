INSERT INTO users (username, password, salt, enabled) VALUES ('admin', '240be518fabd2724ddb6f04eeb1da5967448d7e831c08c8fa822809f74c720a9', '123', 1);
INSERT INTO users (username, password, salt, enabled) VALUES ('user', 'e606e38b0d8c19b24cf0ee3808183162ea7cd63ff7912dbb22b5e803286b4446', '123', 1);
INSERT INTO users (username, password, salt, enabled) VALUES ('zhuyu', 'ee57a953870d6f28c587679d80712108c4a8106b46bcc00fea5efcf0b210934e', '123', 1);


INSERT INTO authorities VALUES 'admin', 'ROLE_ADMIN';
INSERT INTO authorities VALUES 'admin', 'ROLE_USER';
INSERT INTO authorities VALUES 'user', 'ROLE_USER';
INSERT INTO authorities VALUES 'zhuyu', 'ROLE_USER';