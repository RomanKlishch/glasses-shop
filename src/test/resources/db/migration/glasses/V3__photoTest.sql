CREATE TABLE PHOTOS
(
    photo_id   BIGSERIAL primary key,
    glasses_id BIGINT,
    path_to_image    text not null,
    FOREIGN KEY (glasses_id) REFERENCES GLASSES (glasses_id)
);