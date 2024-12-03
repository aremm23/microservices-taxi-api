-- insert_test_drivers.sql
INSERT INTO public.driver (email, created_at, updated_at, firstname, surname, is_free, car_id)
VALUES
    ('aaa1@aa.com', NOW(), NOW(), 'aaa1', 'ccc1', false, null),
    ('bbb2@bb.com', NOW(), NOW(), 'bbb2', 'ccc2', false, null),
    ('ddd3@bb.com', NOW(), NOW(), 'ddd3', 'ddd3', true, null);