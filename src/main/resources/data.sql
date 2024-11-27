INSERT INTO roles(insert_date_time, insert_user_id, last_update_date_time, last_update_user_id, description)
VALUES ('2022-01-05 00:00:00', 1, '2022-01-05 00:00:00', 1, 'Admin'),
       ('2022-01-05 00:00:00', 1, '2022-01-05 00:00:00', 1, 'Manager'),
       ('2022-01-05 00:00:00', 1, '2022-01-05 00:00:00', 1, 'Employee');

insert into users(insert_date_time, insert_user_id, is_deleted, last_update_date_time, last_update_user_id,
                  first_name, gender, last_name, user_name, role_id,pass_Word)
values ('2022-01-05 00:00:00', 1, false, '2022-01-05 00:00:00', 1, 'admin', 'MALE', 'admin', 'admin@admin.com',
        1,'$2a$10$nAB5j9G1c3JHgg7qzhiIXO7cqqr5oJ3LXRNQJKssDUwHXzDGUztNK');

--Abc1
insert into users(insert_date_time, insert_user_id, is_deleted, last_update_date_time, last_update_user_id,
                  first_name, gender, last_name, user_name, role_id,pass_Word)
values ('2022-01-05 00:00:00', 1, false, '2022-01-05 00:00:00', 1, 'Mike', 'MALE', 'Mike', 'mike@gmail.com',
        2,'$2a$10$XG1FCwGLQ7791aD54YNMmuWvjb.OJlXJypidTsYczUzbp3ZDOqpYC');


insert into users(insert_date_time, insert_user_id, is_deleted, last_update_date_time, last_update_user_id,
                  first_name, gender, last_name, user_name, role_id,pass_Word)
values ('2022-01-05 00:00:00', 1, false, '2022-01-05 00:00:00', 1, 'John', 'MALE', 'John', 'john@gmail.com',
        3,'$2a$10$kleV8nRSPOXE94B3oe0DNuLEDg50tSW0KWm84yNdJbAG65GrPxlEW');

