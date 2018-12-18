import { Route } from '@angular/router';

import { RestaurantregisterComponent } from './restaurantregister.component';

export const restaurantregisterRoute: Route = {
    path: 'restaurantregister',
    component: RestaurantregisterComponent,
    data: {
        authorities: []
    }
};
