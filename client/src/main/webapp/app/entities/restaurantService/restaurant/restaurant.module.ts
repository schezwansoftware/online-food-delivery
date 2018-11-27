import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FoodClientSharedModule } from 'app/shared';
import {
    RestaurantComponent,
    RestaurantDetailComponent,
    RestaurantUpdateComponent,
    RestaurantDeletePopupComponent,
    RestaurantDeleteDialogComponent,
    restaurantRoute,
    restaurantPopupRoute
} from './';

const ENTITY_STATES = [...restaurantRoute, ...restaurantPopupRoute];

@NgModule({
    imports: [FoodClientSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RestaurantComponent,
        RestaurantDetailComponent,
        RestaurantUpdateComponent,
        RestaurantDeleteDialogComponent,
        RestaurantDeletePopupComponent
    ],
    entryComponents: [RestaurantComponent, RestaurantUpdateComponent, RestaurantDeleteDialogComponent, RestaurantDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FoodClientRestaurantModule {}
