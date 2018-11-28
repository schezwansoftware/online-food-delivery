import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FoodClientSharedModule } from '../shared';

import { ZOMATO_RESTAURANTS_ROUTE, ZomatoRestaurantsComponent } from './';

@NgModule({
    imports: [FoodClientSharedModule, RouterModule.forRoot([ZOMATO_RESTAURANTS_ROUTE], { useHash: true })],
    declarations: [ZomatoRestaurantsComponent],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FoodClientAppZomatoRestaurantsModule {}
