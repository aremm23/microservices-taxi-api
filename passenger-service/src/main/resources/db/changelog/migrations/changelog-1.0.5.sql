--liquibase formatted sql

--changeset artsem:5
--comment add firstname and surname columns

alter table passenger
    add firstname varchar;

alter table passenger
    add surname varchar;
