create table cst_user_details_aud (id int8 not null, rev int4 not null, revtype int2, birthday date, facebook_id varchar(255), name varchar(255), password varchar(255), username varchar(255), primary key (id, rev));
create table cst_user_details (id int8 not null, birthday date, facebook_id varchar(255), name varchar(255), password varchar(255), username varchar(255), primary key (id));

alter table if exists cst_user_details_aud add constraint FK_cst_user_details_aud foreign key (rev) references revinfo;

alter table if exists student add constraint FK_cst_user_details_student foreign key (id) references cst_user_details;
alter table if exists student_aud add constraint FK_cst_user_details_student_aud foreign key (id, rev) references cst_user_details_aud;
alter table if exists teacher add constraint FK_cst_user_details_teacher foreign key (id) references cst_user_details;
alter table if exists teacher_aud add constraint FK_cst_user_details_teacher_aud foreign key (id, rev) references cst_user_details_aud;

