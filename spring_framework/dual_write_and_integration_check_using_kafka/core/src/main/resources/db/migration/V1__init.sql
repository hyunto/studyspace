CREATE TABLE USERS (
  id bigint unsigned not null auto_increment primary key,
  name varchar(50) not null,
  age int(3) unsigned not null default 1
);
