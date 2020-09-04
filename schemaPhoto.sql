CREATE TABLE photos
(
    photo_id serial primary key,
    glasses_id BIGINT REFERENCES glasses (glasses_id),
    address text not null
);