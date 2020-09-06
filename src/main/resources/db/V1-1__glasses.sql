CREATE TABLE GLASSES
(
    glasses_id BIGSERIAL PRIMARY KEY ,
    name       VARCHAR(240) not null,
    collection VARCHAR(250) not null,
    category   VARCHAR(250) not null,
    details    text         not null,
    price      real
);