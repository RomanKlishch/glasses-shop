CREATE TABLE USERS
(
    user_id  BIGSERIAL primary key,
    name     VARCHAR(240) not null,
    email    VARCHAR(240) not null unique,
    password VARCHAR(240) not null,
    sole     VARCHAR(240) not null,
    role     VARCHAR(10)   not null default 'USER'
);