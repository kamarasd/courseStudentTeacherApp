create table course (id int8 not null, name varchar(255), student_id int8, teacher_id int8, primary key (id));
alter table if exists course add constraint FK_course_student_id foreign key (student_id) references student;
alter table if exists course add constraint FK_course_teacher_id foreign key (teacher_id) references teacher;