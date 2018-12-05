
CREATE TABLE IF NOT EXISTS account.address (
    id uuid,
    latitude text,
    longitude text,
    locality text,
    city text,
    address text,
    type text,
    userId text,
    landmark text,
    PRIMARY KEY(id)
);
