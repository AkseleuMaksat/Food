INSERT INTO t_manufacturer (name, code) VALUES
                                            ('Pizza House', 'PH001'),
                                            ('Burger Lab', 'BL002'),
                                            ('Sushi Master', 'SM003'),
                                            ('Grill Station', 'GS004');

INSERT INTO t_ingredients (name) VALUES
                                     ('Cheese'),
                                     ('Tomato'),
                                     ('Beef'),
                                     ('Onion'),
                                     ('Chicken'),
                                     ('Lettuce'),
                                     ('Pickle'),
                                     ('Bacon'),
                                     ('Mushroom'),
                                     ('Rice'),
                                     ('Salmon'),
                                     ('Cucumber');

INSERT INTO t_foods (name, calories, amounts, price, manu_id) VALUES
                                                                  ('Pepperoni Pizza', 850, 1, 2500, 1),
                                                                  ('Cheeseburger', 650, 1, 1800, 2),
                                                                  ('BBQ Chicken Pizza', 900, 1, 2700, 1),
                                                                  ('Double Beef Burger', 950, 1, 2200, 2),
                                                                  ('Salmon Sushi Roll', 400, 1, 3000, 3),
                                                                  ('Grilled Chicken Plate', 700, 1, 2400, 4),
                                                                  ('Mushroom Pizza', 780, 1, 2300, 1),
                                                                  ('Bacon Burger', 880, 1, 2100, 2);

INSERT INTO t_foods_ingredients (food_id, ingredient_id) VALUES
                                                             -- Pepperoni Pizza
                                                             (1, 1),
                                                             (1, 2),

                                                             -- Cheeseburger
                                                             (2, 1),
                                                             (2, 3),
                                                             (2, 4),

                                                             -- BBQ Chicken Pizza
                                                             (3, 1),
                                                             (3, 2),
                                                             (3, 5),
                                                             (3, 9),

                                                             -- Double Beef Burger
                                                             (4, 1),
                                                             (4, 3),
                                                             (4, 4),
                                                             (4, 7),

                                                             -- Salmon Sushi Roll
                                                             (5, 10),
                                                             (5, 11),
                                                             (5, 12),

                                                             -- Grilled Chicken Plate
                                                             (6, 5),
                                                             (6, 4),
                                                             (6, 6),

                                                             -- Mushroom Pizza
                                                             (7, 1),
                                                             (7, 2),
                                                             (7, 9),

                                                             -- Bacon Burger
                                                             (8, 1),
                                                             (8, 3),
                                                             (8, 8),
                                                             (8, 6);