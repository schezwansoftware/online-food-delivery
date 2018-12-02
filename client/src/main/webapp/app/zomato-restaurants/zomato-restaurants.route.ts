import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { ZomatoRestaurantsComponent } from './';

export const ZOMATO_RESTAURANTS_ROUTE: Route = {
    path: 'zomato-restaurants',
    component: ZomatoRestaurantsComponent,
    data: {
        authorities: [],
        pageTitle: 'zomato-restaurants.title'
    },
    canActivate: [UserRouteAccessService]
};
