create table user
(
    id               serial primary key,
    username         varchar(100) not null,
    encoded_password varchar(80)  not null,
    email            varchar(256) not null unique,
    disabled         boolean      not null
);

create table user_role
(
    user_id serial,
    role    varchar(50),
    foreign key (user_id) references user (id),
    primary key (user_id, role)
);
