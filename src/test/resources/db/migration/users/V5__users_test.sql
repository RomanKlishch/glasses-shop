CREATE TABLE USERS
(
    user_id  BIGSERIAL primary key,
    name     VARCHAR(240) not null,
    email    VARCHAR(240) not null,
    password VARCHAR(240) not null,
    sole     VARCHAR(250) not null,
    role     VARCHAR(6)   not null

);

INSERT INTO USERS
VALUES (1, 'test-ADMIN', 'test-ADMIN', 'test-ADMIN', 'sole', 'ADMIN');
INSERT INTO USERS
VALUES (2, 'test-USER', 'test-USER', 'test-USER', 'sole', 'USER');
INSERT INTO USERS
VALUES (3, 'test-GUEST', 'test-GUEST', 'test-GUEST', 'sole', 'GUEST');