--liquibase formatted sql

--changeset artsem:2
--comment create table 'driver'

create table public.driver
(
    id        bigserial not null
        constraint driver_pk
            primary key,
    email     varchar   not null
        constraint driver_pk_2
            unique,
    firstname varchar,
    surname   varchar,
    is_free   boolean   not null,
    car_id    bigint
        constraint driver_car_id_fk
            references public.car
            on delete set null
);
