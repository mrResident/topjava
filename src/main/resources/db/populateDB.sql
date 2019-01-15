DELETE FROM user_roles;
DELETE FROM meals;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, date_time, description, calories) VALUES
 (100000, '2019-01-08 09:00:00', 'Завтрак', 300),
 (100000, '2019-01-08 12:00:00', 'Обед', 1000),
 (100000, '2019-01-08 18:00:00', 'Ужин', 700),
 (100000, '2019-01-10 08:00:00', 'Завтрак', 500),
 (100000, '2019-01-10 11:45:00', 'Обед', 1000),
 (100000, '2019-01-10 19:20:00', 'Ужин', 700),
 (100001, '2019-01-15 09:30:00', 'Завтрак', 500),
 (100001, '2019-01-15 12:10:00', 'Обед', 1000),
 (100001, '2019-01-15 18:20:00', 'Ужин', 700);