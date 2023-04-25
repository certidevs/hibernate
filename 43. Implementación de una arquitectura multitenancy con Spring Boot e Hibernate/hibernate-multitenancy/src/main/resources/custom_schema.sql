

drop database if exists tenant1;
create database tenant1;
drop table if exists tenant1.employee;
create table tenant1.employee (id bigint auto_increment primary key, name varchar(255));


drop database if exists tenant2;
create database tenant2;
drop table if exists tenant2.employee;
create table tenant2.employee (id bigint auto_increment primary key, name varchar(255));

drop database if exists tenant3;
create database tenant3;
drop table if exists tenant3.employee;
create table tenant3.employee (id bigint auto_increment primary key, name varchar(255));