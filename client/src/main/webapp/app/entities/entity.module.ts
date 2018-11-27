import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FoodClientRestaurantModule as RestaurantServiceRestaurantModule } from './restaurantService/restaurant/restaurant.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        RestaurantServiceRestaurantModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FoodClientEntityModule {}
