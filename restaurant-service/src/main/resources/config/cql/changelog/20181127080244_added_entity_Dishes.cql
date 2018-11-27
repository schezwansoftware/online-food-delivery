
CREATE TABLE IF NOT EXISTS dishes (
    id uuid,
    menuId uuid,
    dishName text,
    dishType text,
    dishPrice double,
    PRIMARY KEY(id)
);
