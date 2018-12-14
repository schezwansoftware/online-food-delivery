export interface IOrderItem {
    id?: string;
    orderId?: string;
    itemName?: string;
    itemId?: string;
    itemPrice?: number;
    itemQuantity?: number;
}

export class OrderItem implements IOrderItem {
    constructor(
        public id?: string,
        public orderId?: string,
        public itemName?: string,
        public itemId?: string,
        public itemPrice?: number,
        public itemQuantity?: number
    ) {}
}
