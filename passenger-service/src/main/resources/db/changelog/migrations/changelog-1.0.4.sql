--liquibase formatted sql

--changeset artsem:4
--comment make email and phone columns not null

alter table passenger
    alter column email set not null;

alter table passenger
    alter column phone set not null;
