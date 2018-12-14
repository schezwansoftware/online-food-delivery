import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { OrderItem } from 'app/shared/model/orderService/order-item.model';
import { OrderItemService } from './order-item.service';
import { OrderItemComponent } from './order-item.component';
import { OrderItemDetailComponent } from './order-item-detail.component';
import { OrderItemUpdateComponent } from './order-item-update.component';
import { OrderItemDeletePopupComponent } from './order-item-delete-dialog.component';
import { IOrderItem } from 'app/shared/model/orderService/order-item.model';

@Injectable({ providedIn: 'root' })
export class OrderItemResolve implements Resolve<IOrderItem> {
    constructor(private service: OrderItemService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((orderItem: HttpResponse<OrderItem>) => orderItem.body));
        }
        return of(new OrderItem());
    }
}

export const orderItemRoute: Routes = [
    {
        path: 'order-item',
        component: OrderItemComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'foodClientApp.orderServiceOrderItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'order-item/:id/view',
        component: OrderItemDetailComponent,
        resolve: {
            orderItem: OrderItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'foodClientApp.orderServiceOrderItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'order-item/new',
        component: OrderItemUpdateComponent,
        resolve: {
            orderItem: OrderItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'foodClientApp.orderServiceOrderItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'order-item/:id/edit',
        component: OrderItemUpdateComponent,
        resolve: {
            orderItem: OrderItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'foodClientApp.orderServiceOrderItem.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const orderItemPopupRoute: Routes = [
    {
        path: 'order-item/:id/delete',
        component: OrderItemDeletePopupComponent,
        resolve: {
            orderItem: OrderItemResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'foodClientApp.orderServiceOrderItem.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
