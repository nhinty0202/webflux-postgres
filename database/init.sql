-- drop database if exists productdb;
-- create database productdb;

\c productdb;

create schema friend_management;

/* add table users */
drop table if exists friend_management.users ;
create table friend_management.users
(
    id    serial primary key,
    name  text not null,
    email text not null
);

/* add table users_relationship */
drop table if exists friend_management.users_relationship ;
create table friend_management.users_relationship
(
    id              serial primary key,
    user_id         integer not null,
    type            integer not null,
    related_user_id integer
);

/* add comment for column type in table users_relationship */
comment on column friend_management.users_relationship.type is '/* 1: Friend 2: Subscribe 3. Block */';

INSERT INTO friend_management.users (id, name, email) VALUES (1, 'User1', 'user1@gmail.com');
INSERT INTO friend_management.users (id, name, email) VALUES (2, 'User2', 'user2@gmail.com');
INSERT INTO friend_management.users (id, name, email) VALUES (3, 'User3', 'user3@gmail.com');
INSERT INTO friend_management.users (id, name, email) VALUES (4, 'User4', 'user4@gmail.com');
INSERT INTO friend_management.users (id, name, email) VALUES (5, 'User5', 'user5@gmail.com');
INSERT INTO friend_management.users (id, name, email) VALUES (6, 'User6', 'user6@gmail.com');
