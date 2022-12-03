alter table cst_user_details add column google_id varchar(255);
alter table cst_user_details_aud add column google_id varchar(255);

alter table if exists cst_user_details_aud add constraint FK_cst_user_details foreign key (rev) references revinfo;

