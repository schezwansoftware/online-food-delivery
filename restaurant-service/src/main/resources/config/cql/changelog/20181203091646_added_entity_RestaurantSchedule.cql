
CREATE TABLE IF NOT EXISTS restaurantSchedule (
    id uuid,
    restaurantId uuid,
    day text,
    openingTime text,
    closingTime text,
    PRIMARY KEY(id)
);
