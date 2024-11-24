create table public.balance
(
    id         bigserial     not null
        constraint balance_pk
            primary key,
    amount     decimal(8, 3) not null,
    user_id    bigint        not null,
    created_at timestamp
);