import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FoodClientSharedModule } from '../shared';

import { CONFIRM_RESTAURANT_ROUTE, ConfirmRestaurantComponent } from './';

@NgModule({
    imports: [FoodClientSharedModule, RouterModule.forRoot([CONFIRM_RESTAURANT_ROUTE], { useHash: true })],
    declarations: [ConfirmRestaurantComponent],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FoodClientAppConfirmRestaurantModule {}
