-- This inserts users with password 'venkatallu' (pre-hashed).
INSERT INTO users (email, password, first_name, last_name, phone_number) VALUES ('venkatallu98@gmail.com', '$2a$10$Se2Xt.Bw95PZezFswBA2u.mUA.LmAQzI3q73p.LdVfw4dtYDaawLO', 'Venkat', 'Allu', '123-456-7890');
INSERT INTO users (email, password, first_name, last_name, phone_number) VALUES ('bhanuprakashbandi09@gmail.com', '$2a$10$Se2Xt.Bw95PZezFswBA2u.mUA.LmAQzI3q73p.LdVfw4dtYDaawLO', 'Bhanu', 'Bandi', '123-456-7890');

-- Sample pantry items for User with ID 1 (Venkat)
INSERT INTO pantry_items (user_id, name, quantity, unit, purchase_date, expiry_date, status) VALUES (1, 'Milk', 1, 'liter', '2025-10-10', '2025-10-15', 'GREEN');
INSERT INTO pantry_items (user_id, name, quantity, unit, purchase_date, expiry_date, status) VALUES (1, 'Eggs', 12, 'pcs', '2025-10-08', '2025-10-22', 'GREEN');
INSERT INTO pantry_items (user_id, name, quantity, unit, purchase_date, expiry_date, status) VALUES (1, 'Chicken Breast', 500, 'grams', '2025-10-11', '2025-10-13', 'ORANGE');

-- Sample pantry items for User with ID 2 (Bhanu)
INSERT INTO pantry_items (user_id, name, quantity, unit, purchase_date, expiry_date, status) VALUES (2, 'Apples', 6, 'pcs', '2025-10-05', '2025-10-12', 'RED');
INSERT INTO pantry_items (user_id, name, quantity, unit, purchase_date, expiry_date, status) VALUES (2, 'Cheddar Cheese', 200, 'grams', '2025-09-20', '2025-11-01', 'GREEN');