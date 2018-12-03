import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FoodClientSharedModule } from 'app/shared';
import {
    RestaurantScheduleComponent,
    RestaurantScheduleDetailComponent,
    RestaurantScheduleUpdateComponent,
    RestaurantScheduleDeletePopupComponent,
    RestaurantScheduleDeleteDialogComponent,
    restaurantScheduleRoute,
    restaurantSchedulePopupRoute
} from './';

const ENTITY_STATES = [...restaurantScheduleRoute, ...restaurantSchedulePopupRoute];

@NgModule({
    imports: [FoodClientSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        RestaurantScheduleComponent,
        RestaurantScheduleDetailComponent,
        RestaurantScheduleUpdateComponent,
        RestaurantScheduleDeleteDialogComponent,
        RestaurantScheduleDeletePopupComponent
    ],
    entryComponents: [
        RestaurantScheduleComponent,
        RestaurantScheduleUpdateComponent,
        RestaurantScheduleDeleteDialogComponent,
        RestaurantScheduleDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FoodClientRestaurantScheduleModule {}
