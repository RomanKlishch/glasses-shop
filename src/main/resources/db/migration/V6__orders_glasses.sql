CREATE TABLE ORDERS_GLASSES
(
    order_glasses_id BIGSERIAL PRIMARY KEY ,
    order_id   BIGINT not null,
    glasses_id BIGINT not null,
    count      SMALLINT not null,
    FOREIGN KEY (order_id) REFERENCES ORDERS (order_id),
    FOREIGN KEY (glasses_id) REFERENCES GLASSES (glasses_id)
);