# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table class_level (
  course_level              varchar(255) not null,
  course                    varchar(255),
  level                     integer,
  constraint pk_class_level primary key (course_level))
;

create table course (
  id                        varchar(255) not null,
  course_name               varchar(255),
  constraint pk_course primary key (id))
;

create table lecture (
  id                        bigint not null,
  course                    varchar(255),
  level                     varchar(255),
  topic                     varchar(255),
  description               TEXT,
  video_id                  varchar(255),
  course_level              varchar(255),
  user_id                   bigint,
  constraint pk_lecture primary key (id))
;

create table study_group (
  id                        bigint not null,
  meet_time                 timestamp,
  course                    varchar(255),
  level                     integer,
  location                  varchar(255),
  topics                    varchar(255),
  course_level              varchar(255),
  user_id                   bigint,
  user_ids                  varchar(255),
  date_created              timestamp,
  constraint pk_study_group primary key (id))
;

create table user_info (
  id                        bigint not null,
  first_name                varchar(255),
  last_name                 varchar(255),
  email                     varchar(255),
  password                  varchar(255),
  profile_pic               varchar(255),
  constraint pk_user_info primary key (id))
;

create sequence class_level_seq;

create sequence course_seq;

create sequence lecture_seq;

create sequence study_group_seq;

create sequence user_info_seq;

alter table lecture add constraint fk_lecture_user_1 foreign key (user_id) references user_info (id) on delete restrict on update restrict;
create index ix_lecture_user_1 on lecture (user_id);
alter table study_group add constraint fk_study_group_user_2 foreign key (user_id) references user_info (id) on delete restrict on update restrict;
create index ix_study_group_user_2 on study_group (user_id);



# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists class_level;

drop table if exists course;

drop table if exists lecture;

drop table if exists study_group;

drop table if exists user_info;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists class_level_seq;

drop sequence if exists course_seq;

drop sequence if exists lecture_seq;

drop sequence if exists study_group_seq;

drop sequence if exists user_info_seq;

