
CREATE TABLE IF NOT EXISTS orderservice.orders (
    id uuid,
    restaurantId uuid,
    userId text,
    status text,
    paymentStatus text,
    totalPrice double,
    deliveryAddress text,
    specialNote text,
    createdAt timestamp,
    lastModifiedAt timestamp,
    lastModifiedBy text,
    PRIMARY KEY(id)
);
