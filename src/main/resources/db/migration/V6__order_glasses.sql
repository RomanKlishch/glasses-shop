CREATE TABLE ORDER_GLASSES
(
    order_id   BIGINT,
    glasses_id BIGINT,
    FOREIGN KEY (order_id) REFERENCES ORDERS (order_id),
    FOREIGN KEY (glasses_id) REFERENCES GLASSES (glasses_id)
);