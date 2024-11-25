-- add_car_to_driver.sql
UPDATE public.driver
SET car_id = 1, updated_at = NOW()
WHERE id = 1;