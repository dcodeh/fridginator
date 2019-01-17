-- Copyright (c) David Cody Burrows 2018
-- This file inserts some items into the database for initial application
-- setup.
-- -----------------------------------------------------------------------------

---- Recommended Items ----

-- Scotch-Brite Sponges
insert into item (name, weekly, unit) values
    ("Scotch-Brite Sponges", 0, "ct");
insert into purchasable_quantity (price, qty, item_id) values
    (6.99, 9, 1),
    (3.99, 3, 1);
insert into inventory (point, item_id, time) values
    ('true', 1, '2019-01-09 12:40:45:00');

-- Dish Soap
insert into item (name, weekly, unit) values
    ("Dish Soap", 0, "fl oz");
insert into purchasable_quantity (price, qty, item_id) values
    (2.29, 16, 2),
    (1.99, 24, 2);
insert into inventory (point, item_id, time) values
    ('true', 2, '2019-01-09 12:40:45:00');

-- Hand Soap
insert into item (name, weekly, unit) values
    ("Hand Soap", 0, "fl oz");
insert into purchasable_quantity (price, qty, item_id) values
    (0.99, 7.5, 3);
insert into inventory (point, item_id, time) values
    ('true', 3, '2019-01-09 12:40:45:00');

-- Toilet Paper
insert into item (name, weekly, unit) values
    ("Toilet Paper", 0, "sq ft");
insert into purchasable_quantity (price, qty, item_id) values
    (15.99, 1400, 4),
    (5.49, 560, 4);
insert into inventory (point, item_id, time) values
    ('true', 4, '2019-01-09 12:40:45:00');

-- Paper Towels
insert into item (name, weekly, unit) values
    ("Paper Towels", 0, "sq ft");
insert into purchasable_quantity (price, qty, item_id) values
    (12.99, 627, 5),
    (3.29, 104.5, 5);
insert into inventory (point, item_id, time) values
    ('true', 5, '2019-01-09 12:40:45:00');

-- Garbage Bags Glad
insert into item (name, weekly, unit) values
    ("Garbage Bags", 0, "ct");
insert into purchasable_quantity (price, qty, item_id) values
    (13.59, 120, 6);
insert into inventory (point, item_id, time) values
    ('true', 6, '2019-01-09 12:40:45:00');

-- Q-Tips
insert into item (name, weekly, unit) values
    ("Q-Tips", 0, "ct");
insert into purchasable_quantity (price, qty, item_id) values
    (6.99, 2000, 7),
    (1.99, 500, 7);
insert into inventory (point, item_id, time) values
    ('true', 7, '2019-01-09 12:40:45:00');

---- Commonly Used Items ----

-- Bagels
insert into item (name, weekly, unit) values
    ("Bagels", 'true', "ct");
insert into purchasable_quantity (price, qty, item_id) values
    (5.49, 6, 8);
insert into inventory (point, item_id, time) values
    ('true', 8, '2019-01-09 12:40:45:00');

-- Bread
insert into item (name, weekly, unit) values
    ("Bread", 'true', "loaf");
insert into purchasable_quantity (price, qty, item_id) values
    (2.99, 2, 9),
    (1.99, 1, 9);
insert into inventory (point, item_id, time) values
    ('true', 9, '2019-01-09 12:40:45:00');

-- Tortillas
insert into item (name, weekly, unit) values
    ("Tortillas", 0, "ct");
insert into purchasable_quantity (price, qty, item_id) values
    (2.49, 8, 10);
insert into inventory (point, item_id, time) values
    ('true', 10, '2019-01-09 12:40:45:00');

-- Milk
insert into item (name, weekly, unit) values
    ("1% Milk", 'true', "gal");
insert into purchasable_quantity (price, qty, item_id) values
    (1.79, 1, 11),
    (1.59, 0.5, 11);
insert into inventory (point, item_id, time) values
    ('true', 11, '2019-01-09 12:40:45:00');

-- Eggs
insert into item (name, weekly, unit) values
    ("Eggs", 'true', "ct");
insert into purchasable_quantity (price, qty, item_id) values
    (0.90, 6, 12),
    (1.59, 12, 12),
    (2.38, 18, 12);
insert into inventory (point, item_id, time) values
    ('true', 12, '2019-01-09 12:40:45:00');

-- Butter
insert into item (name, weekly, unit) values
    ("Salted Butter", 0, "stick");
insert into purchasable_quantity (price, qty, item_id) values
    (2.49, 4, 13);
insert into inventory (point, item_id, time) values
    ('true', 13, '2019-01-09 12:40:45:00');

-- Oats
insert into item (name, weekly, unit) values
    ("Oats", 0, "oz");
insert into purchasable_quantity (price, qty, item_id) values
    (2.99, 42, 14),
    (1.99, 18, 14);
insert into inventory (point, item_id, time) values
    ('true', 14, '2019-01-09 12:40:45:00');

-- Peanuts ???

-- Peanut Butter
insert into item (name, weekly, unit) values
    ("Skippy Peanut Butter", 0, "oz");
insert into purchasable_quantity (price, qty, item_id) values
    (5.69, 40, 15),
    (2.69, 15, 15);
insert into inventory (point, item_id, time) values
    ('true', 15, '2019-01-09 12:40:45:00');

-- Elbows
insert into item (name, weekly, unit) values
    ("Elbow Pasta", 0, "oz");
insert into purchasable_quantity (price, qty, item_id) values
    (0.79, 16, 16);
insert into inventory (point, item_id, time) values
    ('true', 16, '2019-01-09 12:40:45:00');

-- Spaghetti
insert into item (name, weekly, unit) values
    ("Spaghetti", 0, "lb");
insert into purchasable_quantity (price, qty, item_id) values
    (2.79, 2, 17),
    (1.49, 1, 17);
insert into inventory (point, item_id, time) values
    ('true', 17, '2019-01-09 12:40:45:00');

-- Pasta Sauce
insert into item (name, weekly, unit) values
    ("Pasta Sauce", 0, "oz");
insert into purchasable_quantity (price, qty, item_id) values
    (0.99, 24, 18);
insert into inventory (point, item_id, time) values
    ('true', 18, '2019-01-09 12:40:45:00');

-- Ranch Dressing
insert into item (name, weekly, unit) values
    ("Ranch Dressing", 0, "floz");
insert into purchasable_quantity (price, qty, item_id) values
    (7.49, 40, 19),
    (3.29, 16, 19);
insert into inventory (point, item_id, time) values
    ('true', 19, '2019-01-09 12:40:45:00');

-- Italian Dressing
insert into item (name, weekly, unit) values
    ("Italian Dressing", 0, "floz");
insert into purchasable_quantity (price, qty, item_id) values
    (1.29, 16, 20);
insert into inventory (point, item_id, time) values
    ('true', 20, '2019-01-09 12:40:45:00');

-- Salad
insert into item (name, weekly, unit) values
    ("Salad", 0, "head");
insert into purchasable_quantity (price, qty, item_id) values
    (0.99, 1, 21);
insert into inventory (point, item_id, time) values
    ('true', 21, '2019-01-09 12:40:45:00');

-- Carrots ???

-- Raspberries ???

-- Blueberries ???

-- Apples
insert into item (name, weekly, unit) values
    ("Apples", 0, "ct");
insert into purchasable_quantity (price, qty, item_id) values
    (5.99, 24, 22);
insert into inventory (point, item_id, time) values
    ('true', 22, '2019-01-09 12:40:45:00');

-- Bananas
insert into item (name, weekly, unit) values
    ("Bananas", 'true', "ct");
insert into purchasable_quantity (price, qty, item_id) values
    (1.49, 8, 23);
insert into inventory (point, item_id, time) values
    ('true', 23, '2019-01-09 12:40:45:00');

-- Iodized Salt
insert into item (name, weekly, unit) values
    ("Iodized Salt", 0, "oz");
insert into purchasable_quantity (price, qty, item_id) values
    (0.59, 26, 24);
insert into inventory (point, item_id, time) values
    ('true', 24, '2019-01-09 12:40:45:00');

-- Ground Black Pepper
insert into item (name, weekly, unit) values
    ("Ground Black Pepper", 0, "oz");
insert into purchasable_quantity (price, qty, item_id) values
    (1.69, 1.5, 25),
    (2.99, 3, 25);
insert into inventory (point, item_id, time) values
    ('true', 25, '2019-01-09 12:40:45:00');

-- Cinnamon
insert into item (name, weekly, unit) values
    ("Ground Cinnamon", 0, "oz");
insert into purchasable_quantity (price, qty, item_id) values
    (5.99, 7.12, 26),
    (3.49, 2.37, 26);
insert into inventory (point, item_id, time) values
    ('true', 26, '2019-01-09 12:40:45:00');

-- Montreal Chicken Seasoning
insert into item (name, weekly, unit) values
    ("Montreal Chicken Seasoning", 0, "oz");
insert into purchasable_quantity (price, qty, item_id) values
    (2.49, 2.75, 27);
insert into inventory (point, item_id, time) values
    ('true', 27, '2019-01-09 12:40:45:00');

-- Orange Juice
insert into item (name, weekly, unit) values
    ("Frozen Orange Juice", 0, "floz");
insert into purchasable_quantity (price, qty, item_id) values
    (1.99, 48, 28),
    (2.49, 64, 28);
insert into inventory (point, item_id, time) values
    ('true', 28, '2019-01-09 12:40:45:00');

-- Apple Juice
insert into item (name, weekly, unit) values
    ("Frozen Apple Juice", 0, "floz");
insert into purchasable_quantity (price, qty, item_id) values
    (1.69, 48, 29);
insert into inventory (point, item_id, time) values
    ('true', 29, '2019-01-09 12:40:45:00');

-- Default fridge code
insert into fridge (items, users, secret_key, name) values
    ((select count(*) from item),
     (select count(*) from user),
     "ThorBarksTooMuch",
     "The Pink House");
