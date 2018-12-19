create database oauth2provider;
create user 'oauth2provider'@'localhost' identified by 'P@ssw0rd';
grant all on oauth2provider.* to 'oauth2provider'@'localhost';
flush privileges;

use oauth2provider;

-- 클라이언트 등록 테이블
create table oauth_client_details (
  client_id varchar(256) primary key,
  resource_ids varchar(256),
  client_secret varchar(256),
  scope varchar(256),
  authorized_grant_type varchar(256),
  web_server_redirect_url varchar(256),
  authorities varchar(256),
  access_token_validity integer,
  refresh_token_validity integer,
  additional_information varchar(4096),
  autoapprove varchar(256)
);

-- 액세스 토큰 저장 테이블
create table oauth_access_token (
  token_id varchar(256),
  token long varbinary,
  authentication_id varchar(256) primary key,
  user_name varchar(256),
  client_id varchar(256),
  authentication long varbinary,
  refresh_token varchar(256)
);

-- 사용자 승인 테이블
create table oauth_approvals (
  userId varchar(256),
  clientId varchar(256),
  scope varchar(256),
  status varchar(10),
  expiresAt timestamp default '1970-01-01 00:00:01',
  lastModifiedAt timestamp default '1970-01-01 00:00:01'
);

-- 리프레시 토큰 발급 테이블
create table oauth_refresh_token (
  token_id varchar(256),
  token long varbinary,
  authentication long varbinary
);

insert into oauth_client_details(client_id, resource_ids, client_secret, scope, authorized_grant_type, web_server_redirect_url, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove)
value ('clientapp', null, '123456', 'read_profile,read_posts', 'authorization_code', 'https://localhost:8443/callback', null, 3000, -1, null, false);