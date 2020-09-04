CREATE TABLE glasses
(
    glasses_id serial primary key,
    name character VARYING(250) not null,
    collection VARYING(250) not null,
    category VARYING(250) not null,
    details text not null,
    price real
);