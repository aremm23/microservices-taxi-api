-- insert_test_cars.sql
INSERT INTO public.car (model, created_at, updated_at, license_plate, car_category)
VALUES
    ('Bmw M5 F90 rest', NOW(), NOW(), '5555MM-5', 'BUSINESS'),
    ('Bmw 340i G20', NOW(), NOW(), '3333MM-3', 'COMFORT'),
    ('Volvo V90 II', NOW(), NOW(), '8888OO-7', 'BUSINESS');