import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { FoodClientSharedModule } from 'app/shared';
import {
    OrderItemComponent,
    OrderItemDetailComponent,
    OrderItemUpdateComponent,
    OrderItemDeletePopupComponent,
    OrderItemDeleteDialogComponent,
    orderItemRoute,
    orderItemPopupRoute
} from './';

const ENTITY_STATES = [...orderItemRoute, ...orderItemPopupRoute];

@NgModule({
    imports: [FoodClientSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        OrderItemComponent,
        OrderItemDetailComponent,
        OrderItemUpdateComponent,
        OrderItemDeleteDialogComponent,
        OrderItemDeletePopupComponent
    ],
    entryComponents: [OrderItemComponent, OrderItemUpdateComponent, OrderItemDeleteDialogComponent, OrderItemDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class FoodClientOrderItemModule {}
