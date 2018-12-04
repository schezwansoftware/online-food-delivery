import { Moment } from 'moment';
import { Dishes, IDishes } from 'app/shared/model/restaurantService/dishes.model';

export interface IMenuItem {
    endDate?: Moment;
    restaurantId?: string;
    dishes?: Dishes[];
}

export class MenuItemModel implements IMenuItem {
    constructor(public endDate?: Moment, public restaurantId?: string, public dishes?: Dishes[]) {}
}
