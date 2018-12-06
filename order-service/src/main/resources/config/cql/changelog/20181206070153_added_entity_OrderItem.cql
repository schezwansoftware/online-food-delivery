
CREATE TABLE IF NOT EXISTS orderItem (
    id uuid,
    orderId uuid,
    itemName text,
    itemId uuid,
    itemPrice double,
    itemQuantity int,
    PRIMARY KEY(id)
);
