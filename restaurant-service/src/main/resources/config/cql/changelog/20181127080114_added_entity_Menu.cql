
CREATE TABLE IF NOT EXISTS menu (
    id uuid,
    restaurantId uuid,
    startDate tuple<timestamp,varchar>,
    endDate tuple<timestamp,varchar>,
    PRIMARY KEY(id)
);
