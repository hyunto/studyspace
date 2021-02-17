CREATE TABLE user (
  id bigint unsigned not null auto_increment primary key,
  name varchar(50) not null,
  age int(3) unsigned not null default 1,
  key `i_user` (`name`)
);

CREATE TABLE `group` (
    id bigint unsigned not null auto_increment primary key,
    name varchar(50) not null,
    description varchar(200),
    key `i_group_name` (`name`)
);

CREATE TABLE dual_write_inconsistency (
    id bigint unsigned not null auto_increment primary key,
    target_table varchar(100) not null,
    target_id bigint unsigned,
    properties varchar(200),
    action tinyint not null,
    action_datetime timestamp not null default now(),
    key `i_dual_write_inconsistency_target_table_target_id` (`target_table`, `target_id`)
);
