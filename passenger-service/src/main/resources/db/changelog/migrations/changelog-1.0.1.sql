--liquibase formatted sql

--changeset artsem:1
--comment create table 'passenger'

create table passenger
(
    id       bigserial
        constraint passenger_pk
            primary key,
    username varchar
        constraint passenger_pk_2
            unique,
    phone    varchar
        constraint passenger_pk_3
            unique
);
