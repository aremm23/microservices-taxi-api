--liquibase formatted sql

--changeset artsem:2
--comment add updated_at and created_at columns

alter table passenger
    add created_at timestamp;

alter table passenger
    add updated_at timestamp;

