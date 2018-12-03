export interface IRestaurantSchedule {
    id?: string;
    restaurantId?: string;
    day?: string;
    openingTime?: string;
    closingTime?: string;
}

export class RestaurantSchedule implements IRestaurantSchedule {
    constructor(
        public id?: string,
        public restaurantId?: string,
        public day?: string,
        public openingTime?: string,
        public closingTime?: string
    ) {}
}
