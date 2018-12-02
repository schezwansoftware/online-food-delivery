
CREATE TABLE IF NOT EXISTS restaurant (
    id uuid,
    cuisineTypes set<text>,
    currentMenuId uuid,
    executiveLogin text,
    locationId uuid,
    name text,
    registrationDate tuple<timestamp,varchar>,
    PRIMARY KEY(id)
);
