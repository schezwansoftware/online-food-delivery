
CREATE TABLE IF NOT EXISTS orders (
    id uuid,
    restaurantId uuid,
    userId text,
    status text,
    paymentStatus text,
    totalPrice double,
    deliveryAddress text,
    specialNote text,
    PRIMARY KEY(id)
);
