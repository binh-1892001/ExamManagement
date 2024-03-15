USE `exammanagement`;
INSERT INTO user(date_of_birth, email, full_name, gender, password, phone, status, username)
    VALUES ('2000-10-04', 'tirtus@gmail.com', 'Nguyen Van A', 'MALE','$2a$10$IQKi2qaOX.VCs9A45eVXzu.cudImQwTfA3Shp1NYY7yi5SlRHx9Hi', '0351875345', 'ACTIVE', 'admin'),
    ('1999-03-12', 'xiao@gmail.com', 'Nguyen Thi B', 'FEMALE','$2a$10$IQKi2qaOX.VCs9A45eVXzu.cudImQwTfA3Shp1NYY7yi5SlRHx9Hi', '0894936182', 'ACTIVE', 'user01'),
    ('1997-06-11', 'user@gmail.com', 'Nguyen Van C', 'MALE','$2a$10$IQKi2qaOX.VCs9A45eVXzu.cudImQwTfA3Shp1NYY7yi5SlRHx9Hi', '0849798973', 'ACTIVE', 'user02'),
    ('1994-09-18', 'user02@gmail.com', 'Nguyen Thi D', 'FEMALE','$2a$10$IQKi2qaOX.VCs9A45eVXzu.cudImQwTfA3Shp1NYY7yi5SlRHx9Hi', '0238634287', 'INACTIVE', 'user03'),
    ('1992-05-12', 'alatus@gmail.com', 'Le Van E', 'MALE','$2a$10$IQKi2qaOX.VCs9A45eVXzu.cudImQwTfA3Shp1NYY7yi5SlRHx9Hi', '0783648232', 'ACTIVE', 'user04');

insert into role(role_name)
    value
    ('ROLE_ADMIN'),
    ('ROLE_TEACHER'),
    ('ROLE_STUDENT');

insert into user_role(user_id, role_id)
    value
    (1, 1),
    (4, 2),
    (5, 2),
    (2, 3),
    (3, 3);

INSERT INTO classroom(class_name, class_status, status)
    VALUES ('D01', 'NEW', 'ACTIVE'),
    ('D05', 'NEW', 'ACTIVE'),
    ('A02', 'FINISH', 'ACTIVE'),
    ('B06', 'NEW', 'ACTIVE'),
    ('B15', 'OJT', 'ACTIVE'),
    ('E24', 'FINISH', 'ACTIVE'),
    ('F04', 'NEW', 'ACTIVE'),
    ('J20', 'OJT', 'ACTIVE'),
    ('K03', 'NEW', 'ACTIVE'),
    ('C42', 'FINISH', 'ACTIVE'),
    ('A04', 'FINISH', 'ACTIVE'),
    ('B18', 'OJT', 'ACTIVE'),
    ('C21', 'NEW', 'ACTIVE'),
    ('H01', 'NEW', 'ACTIVE'),
    ('K04', 'FINISH', 'ACTIVE'),
    ('FOX', 'OJT', 'ACTIVE'),
    ('JVA', 'OJT', 'ACTIVE'),
    ('HDT', 'FINISH', 'ACTIVE'),
    ('LIX', 'OJT', 'ACTIVE'),
    ('COD', 'FINISH', 'ACTIVE');

INSERT INTO subject(subject_name, status)
    VALUES ('JAVA', true),
    ('PYTHON', true),
    ('JAVASCRIPT', false),
    ('HTML CSS', true),
    ('NODEJS', false),
    ('REACTJS', false),
    ('REACT NATIVE', true),
    ('NEXTJS', true),
    ('TYPESCRIPT', false),
    ('NESTJS', true),
    ('SPRING BOOT', false),
    ('SPRING MVC', false),
    ('MYSQL', true),
    ('SQL SERVER', false),
    ('lINUX', true);

INSERT INTO exam(exam_name, status, subject_id)
    VALUES ('JV01', true, 1),
    ('PY02', true, 2),
    ('JS03', false, 3),
    ('LX04', true, 15),
    ('MS05', false, 13),
    ('SB06', false, 11),
    ('SQ07', true, 14),
    ('RS08', false, 6),
    ('RN09', false, 7),
    ('SC10', true, 12),
    ('NT11', true, 8),
    ('NO12', false, 5),
    ('TT13', true, 9),
    ('HS14', false, 4),
    ('NE15', false, 10);



INSERT INTO test(test_name, test_time, test_type ,resources, status, exam_id)
    VALUES ('Java Core giữa kỳ', 45, 'QUIZTEST', NULL, 'ACTIVE', 1),
    ('T03', 45, 'WRITENTEST', NULL, 'ACTIVE', 2),
    ('T06', 60, 'QUIZTEST', NULL, 'ACTIVE', 3),
    ('T02', 60, 'QUIZTEST', NULL, 'ACTIVE', 4),
    ('T04', 45, 'QUIZTEST', NULL, 'ACTIVE', 5),
    ('T05', 90, 'WRITENTEST', NULL, 'INACTIVE', 6),
    ('T07', 60, 'QUIZTEST', NULL, 'ACTIVE', 7),
    ( 'T09', 45, 'QUIZTEST', NULL, 'ACTIVE', 8),
    ('T08', 60, 'WRITENTEST', NULL, 'ACTIVE', 9),
    ('T10', 90, 'QUIZTEST', NULL, 'ACTIVE', 11),
    ('T12', 45, 'QUIZTEST', NULL, 'ACTIVE', 12),
    ('T14', 50, 'WRITENTEST', NULL, 'INACTIVE', 13),
    ('T13', 60, 'QUIZTEST', NULL, 'INACTIVE', 14),
    ('T11', 90, 'QUIZTEST', NULL, 'ACTIVE', 15),
    ('T15', 45, 'WRITENTEST', NULL, 'INACTIVE', 10),
    ('T16', 45, 'QUIZTEST', NULL, 'ACTIVE', 2),
    ('T19', 60, 'QUIZTEST', NULL, 'INACTIVE', 3),
    ('T17', 45, 'WRITENTEST', NULL, 'ACTIVE',  5);

insert into user_class(user_id, class_id)
    value
    (1, 1),
    (4, 2),
    (3, 3),
    (2, 4),
    (5, 5),
    (2, 6),
    (3, 7),
    (4, 8),
    (1, 9),
    (3, 1),
    (5, 12),
    (1, 13);

insert into class_subject(class_id, subject_id)
    value
    (15, 13),
    (14, 15),
    (13, 14),
    (12, 12),
    (11, 1),
    (10, 11),
    (9, 7),
    (8, 1),
    (7, 4),
    (6, 9),
    (5, 8),
    (4, 5),
    (3, 3),
    (2, 6),
    (1, 2);

# insert into question(content_question, status, type_question, test_id)
#     value
#     ('Noi dung cau hoi', true, 'MULTIPLE', 1),
#     ('Noi dung cau hoi', false, 'SINGLE', 3),
#     ('Noi dung cau hoi', true, 'MULTIPLE', 5),
#     ('Noi dung cau hoi', true, 'SINGLE', 7),
#     ('Noi dung cau hoi', false, 'SINGLE', 9),
#     ('Noi dung cau hoi', true, 'MULTIPLE', 2),
#     ('Noi dung cau hoi', true, 'MULTIPLE', 4),
#     ('Noi dung cau hoi', false, 'SINGLE', 6),
#     ('Noi dung cau hoi', true, 'MULTIPLE', 8),
#     ('Noi dung cau hoi', false, 'SINGLE', 10),
#     ('Noi dung cau hoi', true, 'MULTIPLE', 11),
#     ('Noi dung cau hoi', true, 'SINGLE', 12),
#     ('Noi dung cau hoi', false, 'MULTIPLE', 13),
#     ('Noi dung cau hoi', true, 'MULTIPLE', 14),
#     ('Noi dung cau hoi', true, 'SINGLE', 15);
#
# insert into options(content_options, status, question_id)
#     value
#     ('Lua chon', true, 1),
#     ('Lua chon', false, 3),
#     ('Lua chon', true, 5),
#     ('Lua chon', true, 7),
#     ('Lua chon', false, 9),
#     ('Lua chon', true, 2),
#     ('Lua chon', true, 4),
#     ('Lua chon', false, 6),
#     ('Lua chon', true, 8),
#     ('Lua chon', true, 10),
#     ('Lua chon', true, 11),
#     ('Lua chon', true, 12),
#     ('Lua chon', false, 13),
#     ('Lua chon', false, 14),
#     ('Lua chon', false, 15);