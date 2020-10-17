CREATE TABLE ORDERS
(
    order_id BIGSERIAL primary key,
    user_id  BIGINT not null ,
    status   VARCHAR(20) not null DEFAULT 'CONFIRMED',
    FOREIGN KEY (user_id) REFERENCES USERS (user_id)
);