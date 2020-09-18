CREATE TABLE PHOTOS
(
    photo_id   BIGSERIAL primary key,
    glasses_id BIGINT,
    address    text not null,
    FOREIGN KEY (glasses_id) REFERENCES GLASSES (glasses_id)
);