CREATE TABLE ORDERS
(
    order_id BIGSERIAL primary key,
    user_id  BIGINT,
    status   VARCHAR(20) not null DEFAULT 'confirmed',
    time     TIMESTAMP   not null,
    FOREIGN KEY (user_id) REFERENCES USERS (user_id)
);