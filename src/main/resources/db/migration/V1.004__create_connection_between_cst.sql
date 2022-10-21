create table course_students (courses_id int8 not null, students_id int8 not null, primary key (courses_id, students_id));
create table course_teachers (courses_id int8 not null, teachers_id int8 not null, primary key (courses_id, teachers_id));
alter table if exists course_students add constraint FK_course_students_id foreign key (students_id) references student;
alter table if exists course_students add constraint FK_courses_students_id foreign key (courses_id) references course;
alter table if exists course_teachers add constraint FK_course_teachers_id foreign key (teachers_id) references teacher;
alter table if exists course_teachers add constraint FK_courses_teachers_id foreign key (courses_id) references course;
