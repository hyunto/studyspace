create database clientdb;

create user 'clientdb'@'localhost' identified by '123';
grant all on clientdb.* to 'clientdb'@'localhost';

use clientdb;

create table client_user(
    id bigint auto_increment primary key,
    username varchar(100),
    password varchar(50),
    access_token varchar(100) null,
    access_token_validity datetime null,
    refresh_token varchar(100) null
);
insert into client_user (username, password) values ('jhyunto@', 'P@ssw0rd');
