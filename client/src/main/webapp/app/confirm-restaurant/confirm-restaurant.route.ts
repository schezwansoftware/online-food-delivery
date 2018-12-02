import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { ConfirmRestaurantComponent } from './';

export const CONFIRM_RESTAURANT_ROUTE: Route = {
    path: 'confirm-restaurant/:id',
    component: ConfirmRestaurantComponent,
    data: {
        authorities: [],
        pageTitle: 'confirm-restaurant.title'
    },
    canActivate: [UserRouteAccessService]
};
