import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { FoodClientRestaurantModule as RestaurantServiceRestaurantModule } from './restaurantService/restaurant/restaurant.module';
import { FoodClientLocationModule as RestaurantServiceLocationModule } from './restaurantService/location/location.module';
import { FoodClientMenuModule as RestaurantServiceMenuModule } from './restaurantService/menu/menu.module';
import { FoodClientDishesModule as RestaurantServiceDishesModule } from './restaurantService/dishes/dishes.module';
import { FoodClientRestaurantScheduleModule as RestaurantServiceRestaurantScheduleModule } from './restaurantService/restaurant-schedule/restaurant-schedule.module';
import { FoodClientOrdersModule as OrderServiceOrdersModule } from './orderService/orders/orders.module';
import { FoodClientOrderItemModule as OrderServiceOrderItemModule } from './orderService/order-item/order-item.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        RestaurantServiceRestaurantModule,
        RestaurantServiceLocationModule,
        RestaurantServiceMenuModule,
        RestaurantServiceDishesModule,
        RestaurantServiceRestaurantScheduleModule,
        OrderServiceOrdersModule,
        OrderServiceOrderItemModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FoodClientEntityModule {}
