CREATE TABLE PHOTOS
(
    photo_id   long primary key auto_increment,
    glasses_id long,
    address    text not null,
    foreign key (glasses_id) references GLASSES (glasses_id)
);