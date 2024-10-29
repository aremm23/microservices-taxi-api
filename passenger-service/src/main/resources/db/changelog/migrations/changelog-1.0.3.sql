--liquibase formatted sql

--changeset artsem:3
--comment rename username column to email

alter table passenger
    rename column username to email;

