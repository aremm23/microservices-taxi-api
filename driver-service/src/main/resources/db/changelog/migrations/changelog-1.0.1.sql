--liquibase formatted sql

--changeset artsem:1
--comment create table 'car'

create table public.car
(
    id            bigserial not null
        constraint car_pk
            primary key,
    model         varchar   not null,
    license_plate varchar   not null,
    car_class     varchar   not null,
    created_at    timestamp,
    update_at     timestamp
);
