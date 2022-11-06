create table course_timetables (courses_id int8 not null, timetables_id int8 not null, primary key (courses_id, timetables_id));
create table course_timetables_aud (rev int4 not null, courses_id int8 not null, timetables_id int8 not null, revtype int2, primary key (rev, courses_id, timetables_id));
create table timetable (id int8 not null, lesson_date date, lesson_end time, lesson_start time, primary key (id));
create table timetable_aud (id int8 not null, rev int4 not null, revtype int2, lesson_date date, lesson_end time, lesson_start time, primary key (id, rev));
alter table if exists course_timetables add constraint FK_course_timetables foreign key (timetables_id) references timetable;
alter table if exists course_timetables add constraint FK_courses_timetables foreign key (courses_id) references course;
alter table if exists course_timetables_aud add constraint FK_course_timetable_aud foreign key (rev) references revinfo;
alter table if exists timetable_aud add constraint FK_timetable_aud foreign key (rev) references revinfo;
