USE exammanagement;
INSERT INTO user(date_of_birth, email, full_name, gender, password, phone, status, username)
    VALUES ('2000-10-04', 'admin01@gmail.com', 'Nguyen Van A', 'MALE','$2a$10$IQKi2qaOX.VCs9A45eVXzu.cudImQwTfA3Shp1NYY7yi5SlRHx9Hi', '0351875345', 'ACTIVE', 'admin'),
       ('1999-03-12', 'user01@gmail.com', 'Nguyen Thi B', 'FEMALE','$2a$10$IQKi2qaOX.VCs9A45eVXzu.cudImQwTfA3Shp1NYY7yi5SlRHx9Hi', '0894936182', 'ACTIVE', 'user01'),
       ('1997-06-11', 'user02@gmail.com', 'Nguyen Van C', 'MALE','$2a$10$IQKi2qaOX.VCs9A45eVXzu.cudImQwTfA3Shp1NYY7yi5SlRHx9Hi', '0849798973', 'ACTIVE', 'user02'),
       ('1994-09-18', 'user03@gmail.com', 'Nguyen Thi D', 'FEMALE','$2a$10$IQKi2qaOX.VCs9A45eVXzu.cudImQwTfA3Shp1NYY7yi5SlRHx9Hi', '0238634287', 'INACTIVE', 'user03'),
       ('1992-05-12', 'user04@gmail.com', 'Le Van E', 'MALE','$2a$10$IQKi2qaOX.VCs9A45eVXzu.cudImQwTfA3Shp1NYY7yi5SlRHx9Hi', '0783648232', 'ACTIVE', 'user04'),
       ('1992-02-12', 'user05@gmail.com', 'Nguyen Van F', 'FEMALE','$2a$10$IQKi2qaOX.VCs9A45eVXzu.cudImQwTfA3Shp1NYY7yi5SlRHx9Hi', '0125236524', 'ACTIVE', 'user05'),
       ('1992-05-17', 'user06@gmail.com', 'Tran Van G', 'MALE','$2a$10$IQKi2qaOX.VCs9A45eVXzu.cudImQwTfA3Shp1NYY7yi5SlRHx9Hi', '0785698635', 'ACTIVE', 'user06');
INSERT INTO role(role_name)
    VALUES ('ROLE_ADMIN'),
       ('ROLE_TEACHER'),
       ('ROLE_STUDENT');
INSERT INTO user_role(user_id, role_id)
    VALUES (1, 1),
       (4, 2),
       (5, 2),
       (2, 3),
       (3, 3),
       (6, 2),
       (7, 3);
INSERT INTO classroom(class_name, class_status, teacher_id)
    VALUES ('D01', 'NEW',4),
       ('D05', 'NEW',5),
       ('A02', 'FINISH',6),
       ('B06', 'NEW',5),
       ('B15', 'OJT',4),
       ('E24', 'FINISH',6),
       ('F04', 'NEW',4),
       ('J20', 'OJT',5),
       ('K03', 'NEW',6),
       ('C42', 'FINISH',6);
INSERT INTO subject(subject_name, status)
    VALUES ('JAVA', 'ACTIVE'),
       ('PYTHON', 'ACTIVE'),
       ('JAVASCRIPT', 'ACTIVE'),
       ('HTML CSS', 'ACTIVE'),
       ('NODEJS', 'ACTIVE'),
       ('REACTJS', 'ACTIVE'),
       ('REACT NATIVE', 'ACTIVE'),
       ('NEXTJS', 'ACTIVE'),
       ('TYPESCRIPT', 'ACTIVE'),
       ('NESTJS', 'ACTIVE'),
       ('SPRING BOOT', 'ACTIVE'),
       ('SPRING MVC', 'ACTIVE'),
       ('MYSQL', 'ACTIVE'),
       ('SQL SERVER', 'ACTIVE'),
       ('lINUX', 'ACTIVE');
INSERT INTO exam(exam_name, status, subject_id)
    VALUES ('JV01', 'ACTIVE', 1),
       ('PY02', 'ACTIVE', 2),
       ('JS03', 'ACTIVE', 3),
       ('LX04', 'ACTIVE', 15),
       ('MS05', 'ACTIVE', 13),
       ('SB06', 'ACTIVE', 11),
       ('SQ07', 'ACTIVE', 14),
       ('RS08', 'ACTIVE', 6),
       ('RN09', 'ACTIVE', 7),
       ('SC10', 'ACTIVE', 12),
       ('NT11', 'ACTIVE', 8),
       ('NO12', 'ACTIVE', 5),
       ('TT13', 'ACTIVE', 9),
       ('HS14', 'ACTIVE', 4),
       ('NE15', 'ACTIVE', 10);
INSERT INTO test(test_name, status, test_time, test_type, exam_id)
    VALUES ('T01', 'ACTIVE', 45, 'QUIZTEST', 1),
       ('T018', 'ACTIVE', 120, 'WRITENTEST', 1),
       ('T03', 'ACTIVE', 45, 'WRITENTEST', 2),
       ('T06', 'ACTIVE', 60, 'QUIZTEST', 3),
       ('T02', 'ACTIVE', 60, 'QUIZTEST', 4),
       ('T04', 'ACTIVE', 45, 'QUIZTEST', 5),
       ('T05', 'ACTIVE', 90, 'WRITENTEST', 6),
       ('T07', 'ACTIVE', 60, 'QUIZTEST', 7),
       ('T09', 'ACTIVE', 45, 'QUIZTEST', 8),
       ('T08', 'ACTIVE', 60, 'WRITENTEST', 9),
       ('T10', 'ACTIVE', 90, 'QUIZTEST', 11),
       ('T12', 'ACTIVE', 45, 'QUIZTEST', 12),
       ('T14', 'ACTIVE', 50, 'WRITENTEST', 13),
       ('T13', 'ACTIVE', 60, 'QUIZTEST', 14),
       ('T11', 'ACTIVE', 90, 'QUIZTEST', 15),
       ('T15', 'ACTIVE', 45, 'WRITENTEST', 10),
       ('T16', 'ACTIVE', 45, 'QUIZTEST', 2),
       ('T19', 'ACTIVE', 60, 'QUIZTEST', 3),
       ('T17', 'ACTIVE', 45, 'WRITENTEST', 5);
INSERT INTO user_class(user_id, class_id)
    VALUES (1, 1),
       (3, 2),
       (7, 3),
       (2, 4),
       (3, 5),
       (7, 6),
       (2, 7);
INSERT INTO class_subject(class_id, subject_id)
    VALUES (10, 11),
       (9, 7),
       (8, 1),
       (7, 4),
       (6, 9),
       (5, 8),
       (4, 5),
       (3, 3),
       (2, 6),
       (1, 2);
INSERT INTO question(question_content, status, question_type, question_level, test_id)
    VALUES ('Câu hỏi 1 về Java?', true, 'SINGLE','EASY', 1),
       ('Câu hỏi 2 về Java?', true, 'SINGLE','NORMAL', 1),
       ('Câu hỏi 3 về Java?', true, 'SINGLE','NORMAL', 1),
       ('Câu hỏi 4 về Java?', true, 'SINGLE','NORMAL', 1),
       ('Câu hỏi 5 về Java?', true, 'SINGLE','NORMAL', 1),
       ('Câu hỏi 6 về Java?', true, 'SINGLE','NORMAL', 1),
       ('Câu hỏi 7 về Java?', true, 'SINGLE','NORMAL', 1),
       ('Câu hỏi 8 về Java?', true, 'SINGLE','NORMAL', 1),
       ('Câu hỏi 9 về Java?', true, 'SINGLE','NORMAL', 1),
       ('Câu hỏi 10 về Java?', true, 'SINGLE','NORMAL', 1),
       ('Câu hỏi 1 về JavaScript?', true, 'SINGLE','NORMAL', 1),
       ('Câu hỏi 2 về JavaScript?', true, 'SINGLE','NORMAL', 1),
       ('Câu hỏi 3 về JavaScript?', true, 'SINGLE','NORMAL', 1),
       ('Câu hỏi 4 về JavaScript?', true, 'SINGLE','NORMAL', 1),
       ('Câu hỏi 5 về JavaScript?', true, 'SINGLE','NORMAL', 1),
       ('Câu hỏi 6 về JavaScript?', true, 'SINGLE','NORMAL', 1),
       ('Câu hỏi 7 về JavaScript?', true, 'SINGLE','NORMAL', 1),
       ('Câu hỏi 8 về JavaScript?', true, 'SINGLE','NORMAL', 1),
       ('Câu hỏi 9 về JavaScript?', true, 'SINGLE','NORMAL', 1),
       ('Câu hỏi 10 về JavaScript?', true, 'SINGLE','NORMAL', 1);
INSERT INTO options(option_content, is_correct, question_id)
    VALUES ('Lựa chọn 1 cho câu hỏi 1 về Java', 'CORRECT', 1),
       ('Lựa chọn 2 cho câu hỏi 1 về Java', 'INCORRECT', 1),
       ('Lựa chọn 3 cho câu hỏi 1 về Java', 'INCORRECT', 1),
       ('Lựa chọn 4 cho câu hỏi 1 về Java', 'INCORRECT', 1),
       ('Lựa chọn 1 cho câu hỏi 2 về Java', 'INCORRECT', 2),
       ('Lựa chọn 2 cho câu hỏi 2 về Java', 'CORRECT', 2),
       ('Lựa chọn 3 cho câu hỏi 2 về Java', 'INCORRECT', 2),
       ('Lựa chọn 4 cho câu hỏi 2 về Java', 'INCORRECT', 2),
       ('Lựa chọn 1 cho câu hỏi 3 về Java', 'INCORRECT', 3),
       ('Lựa chọn 2 cho câu hỏi 3 về Java', 'INCORRECT', 3),
       ('Lựa chọn 3 cho câu hỏi 3 về Java', 'INCORRECT', 3),
       ('Lựa chọn 4 cho câu hỏi 3 về Java', 'CORRECT', 3),
       ('Lựa chọn 1 cho câu hỏi 4 về Java', 'INCORRECT', 4),
       ('Lựa chọn 2 cho câu hỏi 4 về Java', 'INCORRECT', 4),
       ('Lựa chọn 3 cho câu hỏi 4 về Java', 'CORRECT', 4),
       ('Lựa chọn 4 cho câu hỏi 4 về Java', 'INCORRECT', 4),
       ('Lựa chọn 1 cho câu hỏi 5 về Java', 'CORRECT', 5),
       ('Lựa chọn 2 cho câu hỏi 5 về Java', 'INCORRECT', 5),
       ('Lựa chọn 3 cho câu hỏi 5 về Java', 'INCORRECT', 5),
       ('Lựa chọn 4 cho câu hỏi 5 về Java', 'INCORRECT', 5),
       ('Lựa chọn 1 cho câu hỏi 6 về Java', 'INCORRECT', 6),
       ('Lựa chọn 2 cho câu hỏi 6 về Java', 'CORRECT', 6),
       ('Lựa chọn 3 cho câu hỏi 6 về Java', 'INCORRECT', 6),
       ('Lựa chọn 4 cho câu hỏi 6 về Java', 'INCORRECT', 6),
       ('Lựa chọn 1 cho câu hỏi 7 về Java', 'INCORRECT', 7),
       ('Lựa chọn 2 cho câu hỏi 7 về Java', 'INCORRECT', 7),
       ('Lựa chọn 3 cho câu hỏi 7 về Java', 'INCORRECT', 7),
       ('Lựa chọn 4 cho câu hỏi 7 về Java', 'CORRECT', 7),
       ('Lựa chọn 1 cho câu hỏi 8 về Java', 'CORRECT', 8),
       ('Lựa chọn 2 cho câu hỏi 8 về Java', 'INCORRECT', 8),
       ('Lựa chọn 3 cho câu hỏi 8 về Java', 'INCORRECT', 8),
       ('Lựa chọn 4 cho câu hỏi 8 về Java', 'INCORRECT', 8),
       ('Lựa chọn 1 cho câu hỏi 9 về Java', 'INCORRECT', 9),
       ('Lựa chọn 2 cho câu hỏi 9 về Java', 'CORRECT', 9),
       ('Lựa chọn 3 cho câu hỏi 9 về Java', 'INCORRECT', 9),
       ('Lựa chọn 4 cho câu hỏi 9 về Java', 'INCORRECT', 9),
       ('Lựa chọn 1 cho câu hỏi 10 về Java', 'INCORRECT', 10),
       ('Lựa chọn 2 cho câu hỏi 10 về Java', 'INCORRECT', 10),
       ('Lựa chọn 3 cho câu hỏi 10 về Java', 'INCORRECT', 10),
       ('Lựa chọn 4 cho câu hỏi 10 về Java', 'CORRECT', 10),
       ('Lựa chọn 1 cho câu hỏi 1 về Javascript', 'CORRECT', 11),
       ('Lựa chọn 2 cho câu hỏi 1 về Javascript', 'INCORRECT', 11),
       ('Lựa chọn 3 cho câu hỏi 1 về Javascript', 'INCORRECT', 11),
       ('Lựa chọn 4 cho câu hỏi 1 về Javascript', 'INCORRECT', 11),
       ('Lựa chọn 1 cho câu hỏi 2 về Javascript', 'INCORRECT', 12),
       ('Lựa chọn 2 cho câu hỏi 2 về Javascript', 'INCORRECT', 12),
       ('Lựa chọn 3 cho câu hỏi 2 về Javascript', 'CORRECT', 12),
       ('Lựa chọn 4 cho câu hỏi 2 về Javascript', 'INCORRECT', 12),
       ('Lựa chọn 1 cho câu hỏi 3 về Javascript', 'INCORRECT', 13),
       ('Lựa chọn 2 cho câu hỏi 3 về Javascript', 'INCORRECT', 13),
       ('Lựa chọn 3 cho câu hỏi 3 về Javascript', 'CORRECT', 13),
       ('Lựa chọn 4 cho câu hỏi 3 về Javascript', 'INCORRECT', 13),
       ('Lựa chọn 1 cho câu hỏi 4 về Javascript', 'INCORRECT', 14),
       ('Lựa chọn 2 cho câu hỏi 4 về Javascript', 'INCORRECT', 14),
       ('Lựa chọn 3 cho câu hỏi 4 về Javascript', 'CORRECT', 14),
       ('Lựa chọn 4 cho câu hỏi 4 về Javascript', 'INCORRECT', 14),
       ('Lựa chọn 1 cho câu hỏi 5 về Javascript', 'CORRECT', 15),
       ('Lựa chọn 2 cho câu hỏi 5 về Javascript', 'INCORRECT', 15),
       ('Lựa chọn 3 cho câu hỏi 5 về Javascript', 'INCORRECT', 15),
       ('Lựa chọn 4 cho câu hỏi 5 về Javascript', 'INCORRECT', 15),
       ('Lựa chọn 1 cho câu hỏi 6 về Javascript', 'INCORRECT', 16),
       ('Lựa chọn 2 cho câu hỏi 6 về Javascript', 'CORRECT', 16),
       ('Lựa chọn 3 cho câu hỏi 6 về Javascript', 'INCORRECT', 16),
       ('Lựa chọn 4 cho câu hỏi 6 về Javascript', 'INCORRECT', 16),
       ('Lựa chọn 1 cho câu hỏi 7 về Javascript', 'CORRECT', 17),
       ('Lựa chọn 2 cho câu hỏi 7 về Javascript', 'INCORRECT', 17),
       ('Lựa chọn 3 cho câu hỏi 7 về Javascript', 'INCORRECT', 17),
       ('Lựa chọn 4 cho câu hỏi 7 về Javascript', 'INCORRECT', 17),
       ('Lựa chọn 1 cho câu hỏi 8 về Javascript', 'INCORRECT', 18),
       ('Lựa chọn 2 cho câu hỏi 8 về Javascript', 'INCORRECT', 18),
       ('Lựa chọn 3 cho câu hỏi 8 về Javascript', 'CORRECT', 18),
       ('Lựa chọn 4 cho câu hỏi 8 về Javascript', 'INCORRECT', 18),
       ('Lựa chọn 1 cho câu hỏi 9 về Javascript', 'CORRECT', 19),
       ('Lựa chọn 2 cho câu hỏi 9 về Javascript', 'INCORRECT', 19),
       ('Lựa chọn 3 cho câu hỏi 9 về Javascript', 'INCORRECT', 19),
       ('Lựa chọn 4 cho câu hỏi 9 về Javascript', 'INCORRECT', 19),
       ('Lựa chọn 1 cho câu hỏi 10 về Javascript', 'INCORRECT', 20),
       ('Lựa chọn 2 cho câu hỏi 10 về Javascript', 'INCORRECT', 20),
       ('Lựa chọn 3 cho câu hỏi 10 về Javascript', 'CORRECT', 20),
       ('Lựa chọn 4 cho câu hỏi 10 về Javascript', 'INCORRECT', 20);
