--liquibase formatted sql

--changeset artsem:5
--comment remove phone column

alter table passenger
    drop column phone;
