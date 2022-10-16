create sequence hibernate_sequence start 1 increment 1;
create table course (id int8 not null, name varchar(255), student_id int8, teacher_id int8, primary key (id));
create table student (id int8 not null, birthdate date, name varchar(255), semester int4, primary key (id));
create table teacher (id int8 not null, birthdate date, name varchar(255), primary key (id));
alter table if exists course add constraint FKs8aqr642dup1oio7xa51t5vw5 foreign key (student_id) references student;
alter table if exists course add constraint FKsybhlxoejr4j3teomm5u2bx1n foreign key (teacher_id) references teacher;
