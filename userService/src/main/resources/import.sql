INSERT INTO `users` (user_name, password, enabled, name, last_name, email) VALUES ('ivan','$2a$10$yVqPOPQUW1wsORCXcSf51OU/IkQOtZY0iT/I6iiF6ZdK/eqlcJiyC',1, 'Ivan', 'Reyes','ivan@email.com');
INSERT INTO `users` (user_name, password, enabled, name, last_name, email) VALUES ('admin','$2a$10$/EndFR6p65N2i3Brgxyf7OdxX6ZkFEJuNMszBhcCjXyEvUi.7HRxW',1, 'John', 'Doe','jhon.doe@email.com');

INSERT INTO `roles` (name) VALUES ('ROLE_USER');
INSERT INTO `roles` (name) VALUES ('ROLE_ADMIN');

INSERT INTO `users_roles` (user_id, role_id) VALUES (1, 1);
INSERT INTO `users_roles` (user_id, role_id) VALUES (2, 2);
INSERT INTO `users_roles` (user_id, role_id) VALUES (2, 1);