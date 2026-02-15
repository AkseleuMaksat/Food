CREATE TABLE t_manufacturer (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(5) NOT NULL
);

CREATE TABLE t_ingredients (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE t_foods (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255),
    calories INTEGER,
    amounts INTEGER,
    price INTEGER,
    manu_id BIGINT NOT NULL,
    CONSTRAINT fk_food_manufacturer
        FOREIGN KEY (manu_id)
            REFERENCES t_manufacturer(id)
);

CREATE TABLE t_foods_ingredients (
    food_id BIGINT NOT NULL,
    ingredient_id BIGINT NOT NULL,
    PRIMARY KEY (food_id, ingredient_id),
    CONSTRAINT fk_food
        FOREIGN KEY (food_id) REFERENCES t_foods(id),
    CONSTRAINT fk_ingredient
        FOREIGN KEY (ingredient_id) REFERENCES t_ingredients(id)
);
