
CREATE TABLE IF NOT EXISTS location (
    id uuid,
    address text,
    state text,
    city text,
    locality text,
    longitude double,
    latitude double,
    pincode text,
    countryName text,
    countryid text,
    PRIMARY KEY(id)
);
