export interface IRestaurantLocation {
    name?: string;
    cuisineTypes?: string[];
    locality?: string;
    longitude?: number;
    latitude?: number;
    pincode?: string;
    address?: string;
}

export class RestaurantLocation implements IRestaurantLocation {
    constructor(
        public name?: string,
        public cuisineTypes?: string[],
        public locality?: string,
        public longitude?: number,
        public latitude?: number,
        public pincode?: string,
        public address?: string
    ) {}
}
