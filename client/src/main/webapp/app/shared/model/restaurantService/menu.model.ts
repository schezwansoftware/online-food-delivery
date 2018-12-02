import { Moment } from 'moment';

export interface IMenu {
    id?: string;
    restaurantId?: string;
    startDate?: Moment;
    endDate?: Moment;
}

export class Menu implements IMenu {
    constructor(public id?: string, public restaurantId?: string, public startDate?: Moment, public endDate?: Moment) {}
}
