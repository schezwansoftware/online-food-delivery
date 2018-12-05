import { Moment } from 'moment';
import { Dishes, IDishes } from 'app/shared/model/restaurantService/dishes.model';

export interface IMenuItem {
    date?: Moment;
    restaurantId?: string;
    dishes?: Dishes[];
}

export class MenuItemModel implements IMenuItem {
    constructor(public date?: Moment, public restaurantId?: string, public dishes?: Dishes[]) {}
}
