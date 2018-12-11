export interface IOrders {
    id?: string;
    restaurantId?: string;
    userId?: string;
    status?: string;
    paymentStatus?: string;
    totalPrice?: number;
    deliveryAddress?: string;
    specialNote?: string;
}

export class Orders implements IOrders {
    constructor(
        public id?: string,
        public restaurantId?: string,
        public userId?: string,
        public status?: string,
        public paymentStatus?: string,
        public totalPrice?: number,
        public deliveryAddress?: string,
        public specialNote?: string
    ) {}
}
