-- insert_test_passengers.sql
INSERT INTO public.passenger (email, created_at, updated_at, firstname, surname)
VALUES
    ('aaa1@aa.com', NOW(), NOW(), 'aaa1', 'ccc1'),
    ('bbb2@bb.com', NOW(), NOW(), 'bbb2', 'ccc2'),
    ('ddd3@bb.com', NOW(), NOW(), 'ddd3', 'ddd3');