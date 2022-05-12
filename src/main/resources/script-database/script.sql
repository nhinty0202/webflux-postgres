/* add table users */
create table users
(
    id    serial primary key,
    name  text not null,
    email text not null
);

/* add table users_relationship */
create table users_relationship
(
    id              serial primary key,
    user_id         integer not null,
    type            integer not null,
    related_user_id integer
);

/* add comment for column type in table users_relationship */
comment on column users_relationship.type is '/* 1: Friend 2: Subscribe 3. Block */';