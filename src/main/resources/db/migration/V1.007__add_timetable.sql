create table timetable (id int8 not null, lesson_date date, lesson_end time, lesson_start time, courses_id int8, primary key (id));
create table timetable_aud (id int8 not null, rev int4 not null, revtype int2, lesson_date date, lesson_end time, lesson_start time, courses_id int8, primary key (id, rev));
alter table course add column if not exists semester_type int4 default 0;
alter table course_aud add column if not exists semester_type int4 default 0;
create table holiday (id int4 not null, source_day date, target_day date, primary key (id));
alter table if exists timetable add constraint FK_timetable_course foreign key (courses_id) references course;
alter table if exists timetable_aud add constraint FK_timetable_revinfo foreign key (rev) references revinfo;
