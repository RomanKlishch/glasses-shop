CREATE TABLE USERS
(
    user_id  BIGSERIAL primary key,
    name     VARCHAR(240) not null,
    email    VARCHAR(240) not null,
    password VARCHAR(240) not null,
    role       VARCHAR(6) not null

);