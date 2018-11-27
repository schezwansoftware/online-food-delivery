import { Moment } from 'moment';

export interface IRestaurant {
    id?: string;
    name?: string;
    locationId?: string;
    executiveLogin?: string;
    cuisineTypes?: string;
    currentMenuId?: string;
    registrationDate?: Moment;
}

export class Restaurant implements IRestaurant {
    constructor(
        public id?: string,
        public name?: string,
        public locationId?: string,
        public executiveLogin?: string,
        public cuisineTypes?: string,
        public currentMenuId?: string,
        public registrationDate?: Moment
    ) {}
}
